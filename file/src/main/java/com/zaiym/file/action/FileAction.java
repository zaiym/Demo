package com.zaiym.file.action;

import com.zaiym.file.upload.BlockInfo;
import com.zaiym.file.upload.service.UploadServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@Scope(value = "request")
public class FileAction {

    private static Logger logger = LoggerFactory.getLogger(FileAction.class);

    @RequestMapping(method = {RequestMethod.GET})
    public String index(){
        return "index";
    }

    //目录
    private String dir = "E:\\receive";

    @RequestMapping(value = "check/{md5}/{blockIndex}",method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Long check(@PathVariable String md5, @PathVariable int blockIndex){
        logger.debug("debug info in check......");
        UploadServer service = new UploadServer(dir);
        return service.check(md5, blockIndex);
    }

    @RequestMapping(value = "check/{isCheckMd5}",method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Map<Integer,Long> check(@RequestBody BlockInfo info, @PathVariable Boolean isCheckMd5){
        UploadServer service = new UploadServer(dir);
        return service.check(info, isCheckMd5);
    }
}