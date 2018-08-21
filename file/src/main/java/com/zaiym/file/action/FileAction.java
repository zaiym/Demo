package com.zaiym.file.action;

import com.zaiym.file.upload.service.UploadServer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FileAction {

    @RequestMapping(method = {RequestMethod.GET})
    public String _(){
        return "index";
    }

    //目录
    private String dir = "E:\\receive";

    @RequestMapping(value = "check/{md5}/{blockIndex}",method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public long check(@PathVariable String md5, @PathVariable int blockIndex){
        UploadServer service = new UploadServer(dir);
        return service.check(md5, blockIndex);
    }
}