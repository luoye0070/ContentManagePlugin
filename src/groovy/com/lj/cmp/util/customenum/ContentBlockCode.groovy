package com.lj.cmp.util.customenum

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 15-4-3
 * Time: 上午1:25
 * To change this template use File | Settings | File Templates.
 */
public enum ContentBlockCode {
    CODE_GDXW(0,"滚动新闻"),//滚动新闻
    CODE_YWBB(1,"要闻播报"),//要闻播报
    CODE_RDGZ(2,"热点关注"),//热点关注
    CODE_BWGK(3,"部务公开"),//部务公开
    CODE_HDJJ(4,"活动锦集"),//活动锦集
//    CODE_TP(5,"图片"),//图片
    CODE_WHCC(6,"文化传承"),//文化传承
    CODE_GNXW(7,"国内新闻"),//国内新闻
    CODE_FQXW(8,"福企新闻"),//福企新闻
    CODE_ZXSD(9,"资讯速递"),//资讯速递
    CODE_TXL(10,"通讯录"),//通讯录
    CODE_ZTPD(11,"专题频道"),//专题频道;
    CODE_XFQYGZBJJ(12,"工作部简介");//幸福企业工作部简介;

    public int code
    public String label

    ContentBlockCode(Integer code,String label){
        this.code=code
        this.label=label
    }
    public static String getLable(int code){
        switch (code){
            case CODE_GDXW.code:
                return  CODE_GDXW.label;
            case CODE_YWBB.code:
                return  CODE_YWBB.label;
            case CODE_RDGZ.code:
                return  CODE_RDGZ.label;
            case CODE_BWGK.code:
                return  CODE_BWGK.label;
            case CODE_HDJJ.code:
                return  CODE_HDJJ.label;
//            case CODE_TP.code:
//                return  CODE_TP.label;
            case CODE_WHCC.code:
                return  CODE_WHCC.label;
            case CODE_GNXW.code:
                return  CODE_GNXW.label;
            case CODE_FQXW.code:
                return  CODE_FQXW.label;
            case CODE_ZXSD.code:
                return  CODE_ZXSD.label;
            case CODE_TXL.code:
                return  CODE_TXL.label;
            case CODE_ZTPD.code:
                return  CODE_ZTPD.label;
            case CODE_XFQYGZBJJ.code:
                return CODE_XFQYGZBJJ.label;
            default:
                return "未知编码"
        }
    }
    public static def getCodeList(){
        return [
                CODE_GDXW.code,//滚动新闻
                CODE_YWBB.code,//要闻播报
                CODE_RDGZ.code,//热点关注
                CODE_BWGK.code,//部务公开
                CODE_HDJJ.code,//活动锦集
//                CODE_TP.code,//图片
                CODE_WHCC.code,//文化传承
                CODE_GNXW.code,//国内新闻
                CODE_FQXW.code,//福企新闻
                CODE_ZXSD.code,//资讯速递
                CODE_TXL.code,//通讯录
                CODE_ZTPD.code,//专题频道
                CODE_XFQYGZBJJ.code
        ];
    }
    public static ContentBlockCode[] codes=[
            CODE_GDXW,//滚动新闻
            CODE_YWBB,//要闻播报
            CODE_RDGZ,//热点关注
            CODE_BWGK,//部务公开
            CODE_HDJJ,//活动锦集
//            CODE_TP,//图片
            CODE_WHCC,//文化传承
            CODE_GNXW,//国内新闻
            CODE_FQXW,//福企新闻
            CODE_ZXSD,//资讯速递
            CODE_TXL,//通讯录
            CODE_ZTPD,//专题频道
            CODE_XFQYGZBJJ
    ];
}