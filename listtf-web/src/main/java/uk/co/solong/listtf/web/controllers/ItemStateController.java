package uk.co.solong.listtf.web.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import uk.co.solong.listtf.core.dao.ListDao;
import uk.co.solong.listtf.core.exceptions.SearchFailedException;
import uk.co.solong.listtf.core.pojo.requests.DetailResult;
import uk.co.solong.listtf.core.pojo.requests.ItemState;
import uk.co.solong.restsec.core.annotations.Entitlement;
import uk.co.solong.restsec.core.exceptions.NotAuthenticatedException;
import uk.co.solong.restsec.core.roles.Role;
import uk.co.solong.restsec.core.sessions.SessionManager;

@RestController
@RequestMapping("/api")
public class ItemStateController {
    private static final Logger logger = LoggerFactory.getLogger(ListController.class);
    private final ListDao listDao;
    private final SessionManager sessionManager;

    @Entitlement(mustbe = { Role.USER })
    @RequestMapping("/editItemState")
    public @ResponseBody DetailResult edit(@Valid ItemState itemState, HttpServletRequest request) throws SearchFailedException, NotAuthenticatedException {
        String userId = sessionManager.getUserId(request);
        return listDao.edit(itemState, userId);
    }

    public ItemStateController(ListDao listDao, SessionManager sessionManager) {
        this.listDao = listDao;
        this.sessionManager = sessionManager;
    }

}
