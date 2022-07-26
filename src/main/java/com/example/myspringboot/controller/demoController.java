package com.example.myspringboot.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.myspringboot.entity.Json;
import com.example.myspringboot.entity.Svg;
import com.example.myspringboot.service.jsonService;
import com.example.myspringboot.service.svgService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class demoController {
    @Autowired
    private jsonService jsonService;

    @Autowired
    private svgService svgService;

//    @ResponseBody
//    @RequestMapping(value="/insert",method = RequestMethod.POST)
//    public String insert(@RequestBody Json json_data){
//        System.out.println(" "+json_data +" ");
////        JSONObject jsonObject=new JSONObject(string);
//        int success=jsonService.insert(new Json("sda",json_data.getJson_data()));
//        JSONObject js = new JSONObject();
//        if(success==1){
//            js.put("res",1);
//        }
//        else js.put("res",0);
//        return js.toJSONString();
//    }

    /*
    在数据库中插入json数据
    */
    @ResponseBody
    @RequestMapping(value="/insertJson",method = RequestMethod.POST)
    public String insert(@RequestParam("name") String name,@RequestBody String data){
//        JSONObject json = JSONObject.parseObject(data);
//        String name=json.getString("name");
//        String data=json.getString("json_data");
        Json json1=jsonService.findOnebyName(name);
        int success=-9;
        if(json1!=null) {
            success = -1;
            System.out.println("存在不唯一");
        }else {
            success=jsonService.insert(new Json(name,data));
            System.out.println("插入成功"+success);
        }
        JSONObject js = new JSONObject();
        System.out.println("success==="+success);
        if(success==1){
            js.put("res",1);
        }
        else if(success==-1){
                js.put("res",-1);
            }
        else js.put("res",0);
        return js.toJSONString();
    }
    /*
    修改数据库中Json数据的值
    */
    @ResponseBody
    @RequestMapping(value="/updateJson",method = RequestMethod.PUT)
    public String update(@RequestParam("name") String name,@RequestBody String data){
//        JSONObject json = JSONObject.parseObject(string);
//        String name=json.getString("name");
//        String data=json.getString("json_data");
        Json json1=jsonService.findOnebyName(name);
        int success=-9;
        if(json1==null){
            String s= String.valueOf(JSONObject.parseObject(this.insert(name,data)).get("res"));
            success=Integer.parseInt(s);
        }else {
            json1.setJson_data(data);
            success=jsonService.insert(json1)==1?1:0;
        }

        JSONObject js = new JSONObject();
        js.put("res",success);

        return js.toJSONString();
    }
    /*
    加载数据库中所有表的字段名称
    */
    @ResponseBody
    @RequestMapping(value="/viewAllName",method = RequestMethod.GET)
    public Object viewAllName(){
        List<Json> de= jsonService.selectAll();
        List<Svg> li=svgService.selectAll();
        Map <String,List<String>> map=new HashMap<>();
        List<String> list1=new ArrayList<>();
        List<String> list2=new ArrayList<>();
        for (Json d:de
             ) {
            list1.add(d.getName());
        }
        for (Svg s:li
        ) {
            list2.add(s.getName());
        }
        map.put("json",list1);
        map.put("svg",list2);
        return map;
    }
    /*
加载数据库中所有表的字段名称
*/
    @ResponseBody
    @RequestMapping(value="/viewJsonName",method = RequestMethod.GET)
    public Object viewName1(){
        List<Json> de= jsonService.selectAll();
        List<String> list=new ArrayList<>();

        for (Json d:de
        ) {
            list.add(d.getName());
        }
        return list;
    }
    /*
    加载数据库中所有的json数据并传输至前端
    */
    @ResponseBody
    @RequestMapping(value="/viewAllJson",method = RequestMethod.GET)
    public List<String> viewAllJson(){
        List<Json> de= jsonService.selectAll();
        List<String> list=new ArrayList<>();
        for (Json d:de
        ) {
            list.add(d.getJson_data());
        }
        return list;
    }
    /*
    加载数据库中json数据并传输至前端
    */
    @ResponseBody
    @RequestMapping(value="/loadJson",method = RequestMethod.GET)
    public String load1(String name){

        Json de= jsonService.findOnebyName(name);
        String json=de.getJson_data();
//        System.out.println(json);
        return json;
    }
//    @ResponseBody
//    @RequestMapping(value="updateJson",method = RequestMethod.PUT)
//    public String update(@RequestBody String string){
//        JSONObject json = JSONObject.parseObject(string);
//        String name=json.getString("name");
//        String data=json.getString("json_data");
//        JSONObject js = new JSONObject();
//        if(jsonService.findOnebyName(name)!=null){
//            jsonService.
//        }else {
//            int success=jsonService.insert(new Json(name,data));
//            if(success==1){
//                js.put("res",1);
//            }
//            else js.put("res",0);
//        }
//
//        return js.toJSONString();
//    }
   /*
   根据svg文件名是否唯一update或insert该文件记录
   */
    @ResponseBody
    @RequestMapping(value="/insertSvg",method = RequestMethod.POST)
    public String insert2(@RequestParam("file") MultipartFile file) throws IOException {
        JSONObject js = new JSONObject();
        int success=-9;
        if (file.getOriginalFilename() != null && file.getOriginalFilename().length() > 0 && !file.isEmpty()) {//判断文件名是否存在和文件大小是否大于0
            String path = ResourceUtils.getURL("classpath:").getPath() + "/static/insert/files";//上传路径获取
//            String realPath =request.getServletContext().getRealPath("/")  +"insert";
            String realPath = path.replace('/', '\\').substring(1, path.length());
            final String fileName = file.getOriginalFilename();
            //限制文件上传的类型
            System.out.println(realPath);
            String contentType = fileName.substring(fileName.lastIndexOf(".") + 1);
            if ("svg".equals(contentType)) {//文件类型判断
                String name = fileName.substring(0, fileName.lastIndexOf("."));
                if (svgService.findOnebyName(name) == null) {
                    File newFile = new File(realPath, fileName);
                    //完成文件的上传'
                    if (!newFile.exists()) newFile.mkdirs();
                    System.out.println(newFile.getAbsolutePath());
                    file.transferTo(newFile);
                    success= svgService.insert(new Svg(name, realPath + "\\" + fileName));//插入至数据库
                    System.out.println("success:" + success);
                    if (success == 1) {
                        System.out.println("svg上传成功!");

                    } else {
                        success=0;
                        System.out.println("svg上传失败!");
                    }
                }
                else {
                    System.out.println("文件名已存在!");
                    success=-1;
                }
            }
            else {
                System.out.println("上传格式错误！");
                success=-2;
            }
        }
        else {
            System.out.println("文件不存在或大小为零");
            success=-3;
        }
        js.put("res",success);
        return js.toJSONString();
    }

    /*
   根据svg文件名是否唯一update或insert该文件记录
   */
    @ResponseBody
    @RequestMapping(value="updateSvg",method = RequestMethod.PUT)
    public String update2(@RequestParam("file") MultipartFile file) throws IOException {
        JSONObject js = new JSONObject();
        int success=-9;
        if (svgService.findOnebyName
                (file.getOriginalFilename()
                        .substring(0, file.getOriginalFilename()
                                .lastIndexOf("."))) != null) {//判断文件去后缀名称在数据库中是否唯一,不唯一则直接修改静态文件，数据库字段不需要修改
            String path = ResourceUtils.getURL("classpath:").getPath() + "/static/insert/files";
            String realPath = path.replace('/', '\\').substring(1, path.length());
            String contentType = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);

            File newFile = new File(realPath, file.getOriginalFilename());
            //完成文件的上传'
            if (!newFile.exists()) {//创建文件路径
                newFile.mkdirs();
            }
            try {
                file.transferTo(newFile);
                success = 1;
                System.out.println("修改成功！");
            }catch (Exception e){
                e.printStackTrace();
                success=0;
                System.out.println("修改失败！");
            }
        }
        else {
            this.insert2(file);//不存在唯一同名文件则将其插入至数据库
            String s = String.valueOf(JSONObject.parseObject(this.insert2(file)).get("res"));
            success = Integer.parseInt(s);
        }

        js.put("res",success);
        return js.toJSONString();
    }
//    @RequestMapping(value="/downloadSvg",method = RequestMethod.GET)
//    public ResponseEntity<byte[]> download2(String name) throws IOException {
//        String path= svgService.downloadOnebyName(name).getSvg_data();
//        InputStream inputStream = null;
//        try {
//            inputStream = new FileInputStream(path);
//            byte[] bytesByStream = getBytesByStream(inputStream);
//            return new ResponseEntity<>(bytesByStream,HttpStatus.OK);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }finally {
//            inputStream.close();
//        }
//        return null;
//    }

    /*
   从数据库加载svg文件并将其传输至前端
   */
    @RequestMapping(value="/loadSvg",method = RequestMethod.GET)
    @ResponseBody
    public String load2(String name) throws IOException {
        String path= svgService.findOnebyName(name).getSvg_data();
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(path);
            byte[] bytesByStream = getBytesByStream(inputStream);
            String json=new String(bytesByStream);
//            System.out.println(json);
            return json;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            inputStream.close();
        }
        return null;
    }

    /*
    从数据库删除json数据记录
    */
    @RequestMapping(value="/deleteJson",method = RequestMethod.DELETE)
    @ResponseBody
    public String delete1(String name){
        int success=-9;
        if(jsonService.delete(name)==1)success=1;//删除成功
        else success=0;
        JSONObject js=new JSONObject();
        js.put("res",success);
        return js.toJSONString();
    }

    /*
    从数据库删除svg文件地址记录以及对应在服务器静态文件资源
    */
    @RequestMapping(value="/deleteSvg",method = RequestMethod.DELETE)
    @ResponseBody
    public String delete2(String name){
        int success=-9;
        if(svgService.delete(name)==1)success=1;//删除成功
        else success=0;
        JSONObject js=new JSONObject();
        js.put("res",success);
        return js.toJSONString();
    }

    /*
    svg文件io，字节流读写
    */
    public byte[]  getBytesByStream(InputStream inputStream){
        byte[] bytes = new byte[1024];//缓冲区
        int b;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            while((b = inputStream.read(bytes)) != -1){
                byteArrayOutputStream.write(bytes,0,b); //字节流输出
            }
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
