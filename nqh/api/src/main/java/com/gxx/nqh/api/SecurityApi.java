package com.gxx.nqh.api;

import com.gxx.nqh.entity.Communication;
import com.gxx.nqh.entity.FileURL;
import com.gxx.nqh.entity.UserInfo;
import com.gxx.nqh.exceptions.UsernameExistedException;
import com.gxx.nqh.service.CommunicationService;
import com.gxx.nqh.service.FileUrlService;
import com.gxx.nqh.service.RegisterationService;
import com.gxx.nqh.service.UserInfoService;
import com.gxx.nqh.util.MD5Util;
import com.gxx.nqh.dto.ProfileDto;
import com.gxx.nqh.util.ResponseUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by GXX on 2016/3/28.
 */
@Controller
@RequestMapping("/security")
public class SecurityApi {

    private Logger logger = LogManager.getLogger(SecurityApi.class);

    @Autowired
    private RegisterationService registerationService;

    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Autowired
    private FileUrlService fileUrlService;

    @Autowired
    private CommunicationService communicationService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private UserInfoService userInfoService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletRequestBindingException, IOException {

        ResponseUtil responseUtil = new ResponseUtil();

        try {
            String userName = ServletRequestUtils.getStringParameter(request, "username");
            String password = ServletRequestUtils.getStringParameter(request, "password");
            boolean isLogin = registerationService.login(userName, password);
            HttpSession session = request.getSession();
            if (isLogin) {
                logger.info("一个新用户登陆成功 = " + userName + ", password = " + password);

                //1h登陆失效
                redisTemplate.opsForValue().set(session.getId(), String.valueOf(userInfoService.getUserInfo(userName).getId()), 3600, TimeUnit.SECONDS);

                responseUtil.setCode("OK");
                responseUtil.setMsg(session.getId());
                response.getWriter().print(responseUtil.toString());
                return;
            } else {
                logger.info("A user login field, the username = " + userName + ", password = " + password);
                responseUtil.setCode("FAILED");
                responseUtil.setMsg("登陆失败");
                response.getWriter().print(responseUtil.toString());
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseUtil.setCode("FAILED");
            responseUtil.setMsg("登陆失败");
            response.getWriter().print(responseUtil.toString());
        }
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public void register(HttpServletRequest request, HttpServletResponse response) throws IOException {


        ResponseUtil responseUtil = new ResponseUtil();
        try {

            String password = StringUtils.trimAllWhitespace(ServletRequestUtils.getStringParameter(request, "password"));
            String email = StringUtils.trimAllWhitespace(ServletRequestUtils.getStringParameter(request, "email"));
            String phoneNo = StringUtils.trimAllWhitespace(ServletRequestUtils.getStringParameter(request, "phone"));
            String userName = email;
            String guid = UUID.randomUUID().toString();
            String code = StringUtils.trimAllWhitespace(ServletRequestUtils.getStringParameter(request, "code"));

            UserInfo userInfo = new UserInfo();
            userInfo.setUserName(email);
            userInfo.setGuid(guid);
            userInfo.setPassword(MD5Util.string2MD5(password + guid));
            userInfo.setPhoneNo(phoneNo);
            userInfo.setEmail(email);
            userInfo.setCreatedOn(new Date());


            if (validateCode(email, code)) {
                registerationService.register(userInfo);
                logger.info("A user register success, " + userInfo.toString());

                responseUtil.setCode("OK");
                responseUtil.setMsg("注册成功");
                response.getWriter().print(responseUtil.toString());
            } else {
                responseUtil.setCode("FAILED");
                responseUtil.setMsg("验证码错误");
                response.getWriter().print(responseUtil.toString());
            }

        } catch (UsernameExistedException e) {
            responseUtil.setCode("FAILED");
            responseUtil.setMsg("用户名已被占用，请更换");
            response.getWriter().print(responseUtil.toString());
        } catch (ServletRequestBindingException e) {
            responseUtil.setCode("FAILED");
            responseUtil.setMsg("服务器错误");
            response.getWriter().print(responseUtil.toString());
            e.printStackTrace();
        } catch (IOException e) {
            responseUtil.setCode("FAILED");
            responseUtil.setMsg("服务器错误");
            response.getWriter().print(responseUtil.toString());
            e.printStackTrace();
        }

    }

    boolean validateCode(String email, String code) {
        if (!StringUtils.isEmpty(redisTemplate.opsForValue().get("code:" + email)) && redisTemplate.opsForValue().get("code:" + email).equals(code)) {
            return true;
        }
        return false;
    }

    @RequestMapping(value = "/validation/code", method = RequestMethod.GET)
    public void getValidateCode(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletRequestBindingException {

        ResponseUtil responseUtil = new ResponseUtil();

        try {
            String email = ServletRequestUtils.getStringParameter(request, "email");

            if (StringUtils.isEmpty(redisTemplate.opsForValue().get(email))) {
                String code = String.valueOf(1000 + new Random().nextInt(9000));
                redisTemplate.opsForValue().set("code:" + email, code, 600, TimeUnit.SECONDS);

                String content = "您的注册验证码是：" + String.valueOf(code) + ".";

                Communication communication = new Communication();
                communication.setUserName(email);
                communication.setContent(content);

                communicationService.sendConnunication(communication);

                responseUtil.setCode("OK");
                responseUtil.setMsg("验证码已发送至邮箱，请查收");
                response.getWriter().print(responseUtil.toString());

                logger.info("已发送一封验证码邮件 , email = " + email + ", code = " + code);
            } else {
                responseUtil.setCode("OK");
                responseUtil.setMsg("验证码已发送，请查收");
                response.getWriter().print(responseUtil.toString());
            }
        } catch (Exception e) {
            responseUtil.setCode("FAILED");
            responseUtil.setMsg("服务器错误");
            response.getWriter().print(responseUtil.toString());
            e.printStackTrace();
        }

    }


    @RequestMapping(value = "/user_info/{id}", method = RequestMethod.GET)
    public void getUserInfo(HttpServletRequest request, HttpServletResponse response, @PathVariable("id") long id) throws ServletRequestBindingException, IOException {
        ResponseUtil responseUtil = new ResponseUtil();
        try {
            UserInfo userInfo = hibernateTemplate.load(UserInfo.class, id);
            response.getWriter().print(userInfo.toString());
        } catch (Exception e) {
            e.printStackTrace();
            responseUtil.setCode("FAILED");
            responseUtil.setMsg("服务器错误");
            response.getWriter().print(responseUtil.toString());
        }
    }

    @RequestMapping(value = "/profile", method = RequestMethod.POST)
    public String modifyProfile(HttpServletRequest request, HttpServletResponse response, @RequestParam("schoolCertification") MultipartFile schoolCertification,
                                @RequestParam("idCertification") MultipartFile idCertification) {

        ResponseUtil responseUtil = new ResponseUtil();

        try {

            String address = ServletRequestUtils.getStringParameter(request, "address");
            String schoolName = ServletRequestUtils.getStringParameter(request, "schoolName");
            String realname = ServletRequestUtils.getStringParameter(request, "realName");
            String idCardNo = ServletRequestUtils.getStringParameter(request, "idCardNo");

            UserInfo loginUser = userInfoService.getLoginUser(request);
            loginUser.setAddDetail(address);
            loginUser.setSchoolName(schoolName);
            loginUser.setRealname(realname);
            loginUser.setIdcardNo(idCardNo);

            FileURL fileURL = new FileURL();
            fileURL.setCreatedOn(new Date());
            fileURL.setFileName(UUID.randomUUID().toString() + ".jpg");
            fileURL.setFileSize(schoolCertification.getSize());
            fileURL.setFileType(schoolCertification.getContentType());
            //WEB-INF的上层路径
            String url = "F:/fileupload/" + fileURL.getFileName();
            fileURL.setFileURL("/" + fileURL.getFileName());

            FileURL fileURL1 = new FileURL();
            fileURL1.setCreatedOn(new Date());
            fileURL1.setFileName(UUID.randomUUID().toString() + ".jpg");
            fileURL1.setFileSize(idCertification.getSize());
            fileURL1.setFileType(idCertification.getContentType());
            String url1 = "F:/fileupload/" + fileURL1.getFileName();
            fileURL1.setFileURL("/" + fileURL1.getFileName());

            try {
                schoolCertification.transferTo(new File(url));
                idCertification.transferTo(new File(url1));
            } catch (IOException e) {
                e.printStackTrace();
            }

            fileUrlService.saveFileUrl(fileURL);
            fileUrlService.saveFileUrl(fileURL1);

            loginUser.setSchoolCertification(fileURL.getId());
            loginUser.setIdCertification(fileURL1.getId());

            userInfoService.modifyUserInfo(loginUser);

            return "../uploadSuccess.jsp";
        } catch (Exception e) {
            responseUtil.setCode("FAILED");
            responseUtil.setMsg("服务器发生错误");
            e.printStackTrace();
            try {
                response.getWriter().write(responseUtil.toString());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return "../uploadFailed.jsp";
    }

    @RequestMapping(value = "profile", method = RequestMethod.GET)
    public void getProfileDetail(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ResponseUtil responseUtil;
        try {
            UserInfo loginUser = userInfoService.getLoginUser(request);
            ProfileDto profileDto = new ProfileDto(
                    loginUser.getIsAvailable(),
                    loginUser.getUserName(),
                    loginUser.getRealname(),
                    loginUser.getIdcardNo(),
                    loginUser.getSchoolName(),
                    loginUser.getAddDetail(), "", "");
            responseUtil = new ResponseUtil(profileDto);
        } catch (Exception e) {
            responseUtil = ResponseUtil._ServerError;
            e.printStackTrace();
        }
        responseUtil.write(response);
    }

    @RequestMapping(value = "profileList", method = RequestMethod.GET)
    public void getProfileListDetail(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ResponseUtil responseUtil;
        try {
            List<ProfileDto> profileDtoList = userInfoService.getProfileDtoList();
            responseUtil = new ResponseUtil(profileDtoList);
        } catch (Exception e) {
            responseUtil = ResponseUtil._ServerError;
            e.printStackTrace();
        }
        responseUtil.write(response);
    }

    @RequestMapping(value = "audit_user", method = RequestMethod.GET)
    public void auditUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ResponseUtil responseUtil;
        try {
            userInfoService.auditUser(request.getParameter("userName"));
            responseUtil = new ResponseUtil("OK", "审核成功");
        } catch (Exception e) {
            responseUtil = ResponseUtil._ServerError;
            e.printStackTrace();
        }
        responseUtil.write(response);
    }
}
