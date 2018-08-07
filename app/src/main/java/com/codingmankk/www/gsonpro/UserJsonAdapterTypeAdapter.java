package com.codingmankk.www.gsonpro;

import com.codingmankk.www.gsonpro.Entity.UserJsonAdapter;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * ================================================
 * 版    本：
 * 创建日期：
 * 描    述：用于接管某种类型的序列化和反序列化过程，包含两个注要方法 write(JsonWriter,T) 和 read(JsonReader) 其它的方法都是final
 *          方法并最终调用这两个抽象方法
 *
 *          只要是操作 User.class 那些之前介绍的 @SerializedName 、 FieldNamingStrategy 、 Since 、 Until 、 Expos 通通都黯然失色，失去了
 *           效果，只会调用我们实现的 UserTypeAdapter.write(JsonWriter, User) 方法，我想怎么写就怎么写。
 * 修订历史：
 * ================================================
 */
public class UserJsonAdapterTypeAdapter extends TypeAdapter<UserJsonAdapter> {

    @Override
    public void write(JsonWriter out, UserJsonAdapter value) throws IOException {
        out.beginObject()
                .name("name").value(value.name)
                .name("age").value(value.age)
                .name("email").value(value.emailAddress)
                .endObject();
    }

    @Override
    public UserJsonAdapter read(JsonReader in) throws IOException {
        UserJsonAdapter user = new UserJsonAdapter();
        in.beginObject();
        while (in.hasNext()) {
            switch (in.nextName()) {
                case "name":
                    user.name = in.nextString();
                    break;

                case "age":
                    user.age = in.nextInt();
                    break;

                case "email":
                case "email_address":
                case "emailAddress":
                    user.emailAddress = in.nextString();
                    break;
                default:
                    break;
            }
        }
        in.endObject();

        return user;
    }
}
