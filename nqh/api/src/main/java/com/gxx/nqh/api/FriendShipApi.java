package com.gxx.nqh.api;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gxx.nqh.dto.FriendDto;
import com.gxx.nqh.util.ResponseUtil;
import com.gxx.nqh.entity.Contact;
import com.gxx.nqh.entity.UserInfo;
import com.gxx.nqh.service.ContactService;
import com.gxx.nqh.service.FriendshipService;
import com.gxx.nqh.service.UserInfoService;
import com.gxx.nqh.util.PostRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by ZHUKE on 2016/4/4.
 */
@Controller
@RequestMapping("/friendship")
public class FriendShipApi {

    @Autowired
    private ContactService contactService;

    @Autowired
    private FriendshipService friendshipService;

    @Autowired
    private UserInfoService userInfoService;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public void uploadContacts(HttpServletRequest request, HttpServletResponse response) {
        ResponseUtil responseUtil = new ResponseUtil();
        try {
            String contactsJson = request.getParameter("contacts");

            Gson contacts = new Gson();
            Type type = new TypeToken<Collection<Contact>>() {
            }.getType();
            ArrayList<Contact> contactsList = contacts.fromJson(contactsJson, type);

            UserInfo loginUser = userInfoService.getLoginUser(request);

            contactService.saveFriendShip(loginUser.getId(), contactsList);

            responseUtil.setCode("OK");
            responseUtil.setMsg("联系人列表已更新成功");
            response.getWriter().print(responseUtil.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public void getFriendship(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ResponseUtil responseUtil;
        try {
            String s = new PostRequestUtil().getRequestPayload(request);
            UserInfo loginUser = userInfoService.getLoginUser(request);
            List<FriendDto> friendshipList = friendshipService.getFriendshipList(loginUser.getId());
            responseUtil = new ResponseUtil(friendshipList);
            responseUtil.write(response);
        } catch (Exception e) {
            responseUtil = ResponseUtil._ServerError;
            responseUtil.write(response);
            e.printStackTrace();
        }
    }

}
