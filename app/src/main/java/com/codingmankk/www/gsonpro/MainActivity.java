package com.codingmankk.www.gsonpro;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.JsonReader;
import android.util.JsonWriter;

import com.codingmankk.www.gsonpro.Entity.AuthoritySample;
import com.codingmankk.www.gsonpro.Entity.Category;
import com.codingmankk.www.gsonpro.Entity.Result;
import com.codingmankk.www.gsonpro.Entity.SinceUntilSample;
import com.codingmankk.www.gsonpro.Entity.User;
import com.codingmankk.www.gsonpro.Entity.UserJsonAdapter;
import com.codingmankk.www.gsonpro.Entity.UserNoAnnotation;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.annotations.Expose;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

/**
 * @author CodingMankk
 */

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Logger.init("MainActivity")
                .hideThreadInfo()
                .methodCount(0);


//        ParseBasicDataJson();
//        GenerateBasicJsonData();
//        GenerateParseJsonClass();
//        SerializedNameJson();
//        ParseGenericsJson();
//        ParseGenericsJson2();
//        ParseJsonByHand();
//        GenerateJsonString();
//        GsonBuilder2Null();
//        GsonBuilder2FormatData();
//        annotationExposeFilterGson();
//        versionFilterGson(-1);
//        AuthorityFilterGson();
//        strategyFilterGson();
//        DefaultStringMappingGson();
//        TypeAdapterGson();
//        JsonDeserializerGson();
//        JsonSerializerGson();
        JsonAdapterGson();

        }

    /**
     * [1]解析-->基本数据类型的解析
     */
    private void ParseBasicDataJson() {
        Gson gson = new Gson();
        Integer i = gson.fromJson("100", int.class);  //与下一行有什么区别？ //100
        Double d = gson.fromJson("\"99.99\"", double.class);  //99.99，将字符串的99.99转成double型
        Boolean b = gson.fromJson("true", boolean.class);  //true
        String s = gson.fromJson("String", String.class);  //String

        Logger.d(i);
        Logger.d(d);
        Logger.d(b);
        Logger.d(s);
    }

    /**
     * [2]生成-->基本数据类型
     */

    private void GenerateBasicJsonData() {
        Gson gson = new Gson();
        String jsonNumber = gson.toJson(100);
        String jsonBoolean = gson.toJson(false);
        String jsonString = gson.toJson("String");

        Logger.i(jsonNumber);  //"100"
        Logger.i(jsonBoolean); //"false"
        Logger.i(jsonString);  //"String"
    }

    /**
     * [3]POJO类的解析和生成
     */

    private void GenerateParseJsonClass() {
        Gson gson = new Gson();
        User user = new User("CodingManKK", 18);

        //生成json
        String jsonObject = gson.toJson(user);
        Logger.i("生成json：" + jsonObject);  //生成json：{"age":18,"name":"CodingManKK"}

        //解析json
        String jsonString = "{\"name\":\"CodingManKK2\",\"age\":60}";
        User user1 = gson.fromJson(jsonString, User.class);
        Logger.i("解析json：" + user1.toString()); //解析json：User{name='CodingManKK2', age=60, emailAddress='null'}
        Logger.i(String.valueOf(user1.getAge())); //60
        Logger.i(user1.getEmailAddress());  //No message/exception is set
        Logger.i(user1.getName()); // CodingManKK2

    }

    /**
     * [4] 属性重命名 @SerializedName 注解的使用
     * <p>
     * 当多种情况同时出时，以最后一个出现的值为准
     */

    private void SerializedNameJson() {
        String jsonString = "{\"name\":\"CodingManKK\",\"age\":18,\"emailAddress\":\"11@example.com\",\"email\":\"22@example.com\",\"email_address\":\"33@example.com\"}";
        Gson gson = new Gson();
        User user = gson.fromJson(jsonString, User.class);
        Logger.i(user.getEmailAddress()); // 33@example.com
    }

    /**
     * [5-1]Gson使用泛型
     */
    private void ParseGenericsJson() {
        Gson gson = new Gson();
        String jsonArray = "[\"android\",\"PHP\",\"Java\"]";

        //使用数组
        String[] strings = gson.fromJson(jsonArray, String[].class);
        int lens = strings.length;
        for (int i = 0; i < lens; i++) {
            Logger.i(strings[i]); //android PHP Java
        }

        Logger.i("======================");

        //使用List
        List<String> stringList = gson.fromJson(jsonArray, new TypeToken<List<String>>() {
        }.getType());
        int size = stringList.size();
        for (int i = 0; i < size; i++) {
            Logger.i(stringList.get(i));
        }
    }

    /**
     * [5-2]使用泛型
     */
    private void ParseGenericsJson2() {
        //数据不同：data字段不同
        //真正需要的 data 所包含的数据，而 code 只使用一次， message 则几乎不用。
        //直接定义了：class Result<T>，使用泛型
        String stringJson1 = "{\"code\":\"0\",\"message\":\"success\",\"data\":{}}";
        String stringJson2 = "{\"code\":\"0\",\"message\":\"success\",\"data\":[]}";

        Gson gson = new Gson();

        //需要获取type
        Type type = new TypeToken<Result<User>>() {
        }.getType();
        Result<User> userResult = gson.fromJson(stringJson1, type);
        User user = userResult.data;
        Logger.i(String.valueOf(userResult.code));

        //需要获取type
        Type type1 = new TypeToken<Result<List<User>>>() {
        }.getType();
        Result<List<User>> userListResult = gson.fromJson(stringJson2, type1);
        List<User> users = userListResult.data;
        Logger.i(String.valueOf(userListResult.code));
    }


    /**
     * [6]流式反序列化--手动
     */
    private void ParseJsonByHand() {
        String jsonString = "{\"name\":\"CodingManKK\",\"age\":\"24\"}";
        User user = new User();
        JsonReader jsonReader = new JsonReader(new StringReader(jsonString));
        try {
            jsonReader.beginObject();
            while (jsonReader.hasNext()) {
                String s = jsonReader.nextName();
                switch (s) {
                    case "name":
                        user.name = jsonReader.nextString();
                        break;
                    case "age":
                        user.age = jsonReader.nextInt();
                        break;
                    case "email":
                        user.emailAddress = jsonReader.nextString();
                        break;
                    default:
                        break;
                }
            }
            jsonReader.endObject();
            Logger.i(user.name);
            Logger.i(String.valueOf(user.age));
            Logger.i(user.emailAddress);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * [7]流式反序列化
     */
    private void GenerateJsonString(){
        User user = new User("CodingManKK", 24, "CodingManKK@998.com");
        Gson gson = new Gson();
        //自动流式反序列化
        gson.toJson(user,System.out);// {"age":24,"emailAddress":"CodingManKK@998.com","name":"OzTaking"}

        Logger.i("---------------------");

        //手动方式
        JsonWriter jsonWriter = new JsonWriter(new OutputStreamWriter(System.out));  // {"name":"Lily","age":100,"email":null}
        try {
            jsonWriter.beginObject()
                    .name("name").value("Lily")  //在调用value前必须调用name
                    .name("age").value(100)
                    .name("email").nullValue() //可以设置为空值
                    .endObject();
            jsonWriter.flush();

//            jsonWriter.beginArray() :怎么使用？？？

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * [8-1]使用GsonBuilder 导出null值
     *
     * Gson在默认情况下是不自动导出值为null的键
     *
     * email 字段是没有在json中出现的，当我们在调试是、需要导出完整的json串时或API接中要求没有值必须用Null时，就会比较有用
     */

    private void GsonBuilder2Null(){
        User user = new User("CodingManKK", 24);
        Gson gson = new Gson();
        Logger.i(gson.toJson(user));//{"age":24,"name":"CodingManKK"} --不打印邮件地址为null


        //打印出了邮件地址为null
        Gson gson1 = new GsonBuilder()
                .serializeNulls()
                .create();
        Logger.i(gson1.toJson(user)); //{"age":24,"emailAddress":null,"name":"CodingManKK"}

    }

    /**
     * [8-2]使用GsonBuilder格式化输出、日期时间
     */

    private void GsonBuilder2FormatData(){

        Gson gson = new GsonBuilder()
                .serializeNulls() //序列化null
                .setDateFormat("yyyy-MM-dd-HH:mm:ss") // 设置日期时间格式，另有2个重载方法 // 在序列化和反序化时均生效
                .disableInnerClassSerialization() // 禁此序列化内部类
//                .generateNonExecutableJson() //生成不可执行的Json多了 )]}' 这4个字符）
                .disableHtmlEscaping()//禁止转义html标签
                .setPrettyPrinting()//格式化输出
                .create();

        Date date = new Date(System.currentTimeMillis());
        String s = gson.toJson(date);
        Logger.i(s);

        long l = System.currentTimeMillis();
        String s1 = gson.toJson(l); //1533610799443
        Logger.i(s1);
    }


    //==================字段过滤==============

   /*
    {
        "id": 1,
            "name": "电脑",
            "children": [
                {
                    "id": 100,
                        "name": "笔记本"
                },
                {
                    "id": 101,
                        "name": "台式机"
                }
           ]
    }
    */

   /*public class Category{
       public int id;
       public String name;
       public List<Category> children;
       //parent 字段是因业务需要增加的，那么在序列化是并不需要，所以在序列化 时就必须将其排除
       public Category parent;
   }*/

    /**
     * [9-1]字段过滤：Expose-查看Category类中的注解
     */
    private void annotationExposeFilterGson(){

        String jsonString = "{ \"id\": 1,\"name\": \"电脑\",\"children\": [ {\"id\": 100,\"name\": \"笔记本\"},{\"id\": 101,\"name\": \"台式机\"}]}";

        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();

        Category category = gson.fromJson(jsonString, Category.class);
        Logger.i(String.valueOf(category.getId())); //1
        Logger.i(category.getName());//电脑
        List<Category> children = category.getChildren();
        int size = children.size();
        Category categoryChildren;
        for (int i=0; i<size; i++){
            categoryChildren = children.get(i);
            Logger.i(String.valueOf(categoryChildren.getId())); //100--笔记本 //101-台式机
            Logger.i(categoryChildren.getName());
        }
    }

    /**
     * [9-2]基与版本的字段过滤
     *
     * Gson在对基于版本的字段导出提供了两个注解 @Since 和 @Until ,和
     * GsonBuilder.setVersion(Double) 配合使用。 @Since 和 @Until 都接收一个 Double 值。
     */

    private void versionFilterGson(double version){

        SinceUntilSample sinceUntilSample = new SinceUntilSample();
        sinceUntilSample.since = "Since";
        sinceUntilSample.until = "until";

        Gson gson = new GsonBuilder().setVersion(version).create();
        Logger.i(gson.toJson(sinceUntilSample));

        //version:4 -->{"since":"Since","until":"until"}
        //version:0 -->{"until":"until"}
        //version:5 -->{"since":"Since"}
        //version:-1 -->{"since":"Since","until":"until"}?????

    }

    /**
     * [9-3]基于访问权限修饰符过滤
     *
     * final String finalFiled = "final";  *
     * static String staticFiled = "static";  *
     * public String publicFiled = "public";
     * protected String protectedFiled = "protected";
     * String defaultFiled = "default";
     * private String privateFiled = "private";  *
     */

    private void AuthorityFilterGson(){
        Gson gson = new GsonBuilder()  //排除了final、static、private
                .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.STATIC, Modifier.PRIVATE)
                .create();

        AuthoritySample authoritySample = new AuthoritySample();
        //{"defaultFiled":"default","protectedFiled":"protected","publicFiled":"public"}
        Logger.i(gson.toJson(authoritySample));
    }


    /**
     * [9-4] 基于策略过滤, 序列化为例
     */
    private void strategyFilterGson(){
        Gson gson = new GsonBuilder()
                .addSerializationExclusionStrategy(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        // 此处作判断处理，决定要不要排除该字段,return true为排除
                        if ("finalFiled".equals(f.getName())) {
                            return true;
                        }

                        Expose e = f.getAnnotation(Expose.class);
                        if (e != null && e.deserialize() == false) {
                            return true;
                        }
                        return false;
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        // 直接排除某个类 ，return true为排除
                        if (clazz == int.class || clazz == Integer.class || clazz == String.class) {
                            return true;
                        }
                        return false;
                    }
                }).create();

        AuthoritySample authoritySample = new AuthoritySample();
        Logger.i(gson.toJson(authoritySample));

    }


    /**
     * [10-1]-字段映射规则--默认实现：输出的key的大小写格式不一样
     */

    private void DefaultStringMappingGson(){
        UserNoAnnotation user = new UserNoAnnotation("CodingManKK", 18);
        user.emailAddress = "CodingMankk@123.com";

        Gson gson = new GsonBuilder()
//                .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)                         //"emailAddress":"CodingMankk@123.com"
//                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_DASHES)           //"email-address":"CodingMankk@123.com"
//                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)      //"email_address":"CodingMankk@123.com"
//                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)                 //"EmailAddress":"CodingMankk@123.com"
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE_WITH_SPACES)       //"Email Address":"CodingMankk@123.com"
                .create();

        String s = gson.toJson(user);
        Logger.i(s);
    }

    /**
     * [10-2]-字段映射--自定义映射
     * 注意：@SerializedName 注解拥有最高优先级，在加有 @SerializedName 注解的字段上 FieldNamingStrategy 不生效！确实是！
     */
    private void CustomStringMappingGson(){
        new GsonBuilder()
                .setFieldNamingStrategy(new FieldNamingStrategy() {
                    @Override
                    public String translateName(Field f) {
                        //实现自定义规则
                        return null;
                    }
                }).create();
    }


    /**
     * [11] TypeAdapter:用于接管某种类型的序列化/反序列化过程
     *
     * TypeAdapter 以及 JsonSerializer 和 JsonDeserializer 都需要与GsonBuilder.registerTypeAdapter
     * 或 GsonBuilder.registerTypeHierarchyAdapter 配合使用
     */

    private void TypeAdapterGson(){
        User user = new User("CodingManKK", 100);
        user.emailAddress = "CodingManKK@123.com";

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(User.class, new UserTypeAdapter())
                .create();
        String s = gson.toJson(user);
        Logger.i(s);  // {"name":"CodingManKK","age":100,"email":"CodingManKK@123.com"}
    }

    /**
     * [12]只接管-反-序列化
     */
    private void JsonDeserializerGson(){
        Gson gson = new GsonBuilder().registerTypeAdapter(Integer.class, new JsonDeserializer<Integer>() {
            @Override
            public Integer deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

                try {
//               return json.getAsString();
                    return json.getAsInt();
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    return -1;
                }
            }
        }).create();

        Logger.i(gson.toJson(100));  //100
        Logger.i(String.valueOf(gson.fromJson("\"\"",Integer.class))); //-1

    }

    /**
     * [13] 只管序列化
     */
    private void JsonSerializerGson(){
        JsonSerializer<Number> js = new JsonSerializer<Number>() {
            @Override
            public JsonElement serialize(Number src, Type typeOfSrc, JsonSerializationContext context) {
//                return null;
                return new JsonPrimitive(String.valueOf(src));
            }
        };

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Integer.class, js)
                .registerTypeAdapter(Long.class, js)
                .registerTypeAdapter(Float.class, js)
                .registerTypeAdapter(Double.class, js)
                .create();

        Gson gson1 = new GsonBuilder()
                .registerTypeHierarchyAdapter(Number.class,js)
                .create();

//        Logger.i(gson.toJson(100.0f)); //"100.0" --转化为了字符串
//        Logger.i(gson.toJson(99.892)); //"99.892" --转化为了字符串

        Logger.i(gson1.toJson(100.0f)); //"100.0" --转化为了字符串
        Logger.i(gson1.toJson(99.892)); //"99.892" --转化为了字符串
    }

    /**
     * [14] 注解@JsonAdapter的使用
     */

    private void JsonAdapterGson(){
        Gson gson = new Gson();
        //{"name":"小明","age":38,"email":"xiaoming@123.com"}
        UserJsonAdapter user = new UserJsonAdapter("小明", 38, "xiaoming@123.com");
        Logger.i(gson.toJson(user));
    }

}
