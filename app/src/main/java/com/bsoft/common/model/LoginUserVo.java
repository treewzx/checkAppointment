package com.bsoft.common.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by shengqf
 * Email : shengqf@bsoft.com.cn
 * date : 2018/5/6
 * describe :
 */
public class LoginUserVo implements Parcelable {


    /**
     * birthdate : 948643200000
     * address : 湘潭市岳塘区书院路100号
     * code : 5555
     * certificationValidityPeriod : 1
     * latitude : 0
     * mobile : 15606816762
     * title : 湘潭市第一人民医院
     * orgid : 9857
     * token : 47927a7d5a55484d8e06cdaaebc607b5
     * realname : 跃千愁
     * nationality : null
     * idcard : 650101200001246395
     * header : http://10.8.3.57:8000/upload/image/2018/07/24/1532421808466.jpg
     * id : 12586
     * cardtype : 1
     * sn : dWV/dTsArcqdVqTdIdPN5K3rUxmDjSomECTmHqaalw2vuKNC88uhsaKi139w3Wett6AF4WnW/2eg
     * HLgpEfi9jeDRwkAJVY0g
     * sexcode : 1
     * username : null
     * longitude : 0
     */

    public long birthdate;
    public String address;
    public String code;
    public String certificationValidityPeriod; //1-已认证，2-未认证
    public double latitude;
    public String mobile;
    public String title;
    public int orgid;
    public String token;
    public String realname;
    public String nationality;
    public String idcard;
    public String header;
    public int id; //用户表的主键（对应家庭成员表的uid）
    public String cardtype;
    public String sn;
    public int sexcode;
    public String username;
    public double longitude;

    public String getHospitalCode(){
        return code;
    }

    //我的就诊人页面防止姓名过长
    public String getName(){
        if (realname == null){
            return "";
        }
        if (realname.length()>6){
            return realname.substring(0,6) + "...";
        }
        return realname;
    }

    public boolean isCertificated() {
        if (certificationValidityPeriod != null
                && certificationValidityPeriod.equals("1")) {
            return true;
        }
        return false;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.birthdate);
        dest.writeString(this.address);
        dest.writeString(this.code);
        dest.writeString(this.certificationValidityPeriod);
        dest.writeDouble(this.latitude);
        dest.writeString(this.mobile);
        dest.writeString(this.title);
        dest.writeInt(this.orgid);
        dest.writeString(this.token);
        dest.writeString(this.realname);
        dest.writeString(this.nationality);
        dest.writeString(this.idcard);
        dest.writeString(this.header);
        dest.writeInt(this.id);
        dest.writeString(this.cardtype);
        dest.writeString(this.sn);
        dest.writeInt(this.sexcode);
        dest.writeString(this.username);
        dest.writeDouble(this.longitude);
    }

    public LoginUserVo() {
    }

    protected LoginUserVo(Parcel in) {
        this.birthdate = in.readLong();
        this.address = in.readString();
        this.code = in.readString();
        this.certificationValidityPeriod = in.readString();
        this.latitude = in.readDouble();
        this.mobile = in.readString();
        this.title = in.readString();
        this.orgid = in.readInt();
        this.token = in.readString();
        this.realname = in.readString();
        this.nationality = in.readString();
        this.idcard = in.readString();
        this.header = in.readString();
        this.id = in.readInt();
        this.cardtype = in.readString();
        this.sn = in.readString();
        this.sexcode = in.readInt();
        this.username = in.readString();
        this.longitude = in.readDouble();
    }

    public static final Creator<LoginUserVo> CREATOR = new Creator<LoginUserVo>() {
        @Override
        public LoginUserVo createFromParcel(Parcel source) {
            return new LoginUserVo(source);
        }

        @Override
        public LoginUserVo[] newArray(int size) {
            return new LoginUserVo[size];
        }
    };
}
