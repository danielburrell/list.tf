package uk.co.solong.listtf.web.controllers;

import java.util.ArrayList;
import java.util.List;

import uk.co.solong.restsec.core.loader.AbstractRestSecLoader;
import uk.co.solong.restsec.core.roles.Role;

public class RestSecLoader extends AbstractRestSecLoader {

    @Override
    public List<String> load(String key) throws Exception {
        // TODO Auto-generated method stub
        List<String> list = new ArrayList<String>();
        list.add(Role.USER.toString());
        return list;
    }

}
