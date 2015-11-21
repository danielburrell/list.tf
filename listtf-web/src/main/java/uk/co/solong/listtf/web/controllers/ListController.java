package uk.co.solong.listtf.web.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import uk.co.solong.listtf.core.dao.ListDao;
import uk.co.solong.listtf.core.exceptions.SearchFailedException;
import uk.co.solong.listtf.core.pojo.requests.ErrorCodes;
import uk.co.solong.listtf.core.pojo.requests.ErrorResult;
import uk.co.solong.listtf.core.pojo.requests.ListRequest;
import uk.co.solong.listtf.core.pojo.requests.ListResult;
import uk.co.solong.restsec.core.exceptions.NotAuthenticatedException;

@RestController
@RequestMapping("/api")
public class ListController {

    private static final Logger logger = LoggerFactory.getLogger(ListController.class);
    private final ListDao listDao;

    @RequestMapping("/list")
    public @ResponseBody ListResult list(@Valid ListRequest listRequest, HttpServletRequest request) throws SearchFailedException, NotAuthenticatedException {
        return listDao.searchItems(listRequest);
    }

    @ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
    @ExceptionHandler(SearchFailedException.class)
    public @ResponseBody ErrorResult schemaUnavailable() {
        ErrorResult errorResult = new ErrorResult();
        errorResult.setId(ErrorCodes.SEARCH_FAILED.getId());
        errorResult.setReason("Search is unavailable at this time");
        return errorResult;
    }

    public ListController(ListDao listDao) {
        this.listDao = listDao;
    }
}
