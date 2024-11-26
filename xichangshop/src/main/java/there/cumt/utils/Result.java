package there.cumt.utils;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class Result {
    private Boolean success;  //返回的成功或者失败的标识符
    private Integer code;  //返回的状态码
    private String message; //提示信息
    private Map<String, Object> data = new HashMap<String, Object>();  //数据
    //把构造方法私有
    private Result() {}
    //成功的静态方法
    public static Result ok(){
        Result result =new Result();
        result.setSuccess(true);
        result.setCode(ResultCode.SUCCESS);
        result.setMessage("成功");
        return result;
    }
    //失败的静态方法
    public static Result error(){
        Result result =new Result();
        result.setSuccess(false);
        result.setCode(ResultCode.ERROR);
        result.setMessage("失败");
        return result;
    }
    //使用下面四个方法，方面以后使用链式编程
    // R.ok().success(true)
    //  r.message("ok).data("item",list)
    public Result success(Boolean success){
        this.setSuccess(success);
        return this; //当前对象  R.success(true).message("操作成功").code().data()
    }
    public Result message(String message){
        this.setMessage(message);
        return this;
    }
    public Result code(Integer code){
        this.setCode(code);
        return this;
    }
    public Result data(String key, Object value){
        this.data.put(key, value);
        return this;
    }
    public Result data(Map<String, Object> map){
        this.setData(map);
        return this;
    }
}
