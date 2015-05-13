package com.lj.cmp.tag

class SecondTagLib {
    static namespace = "lj";

    Closure mediaTagConvertor = { attrs ->
        def writer = out
        def playerUrl=resource(dir:"js",file:"vcastr22.swf");
        def mp3playerUrl=resource(dir:"js",file:"audioplayer.swf");
        def htmlStr="<script type='text/javascript'>" +
                "\$(function(){\n" +
                "    \$.each(\$(\"embed\"),function(){\n" +
                "        var url=\$(this).attr(\"src\");\n" +
                "        var width = \$(this).attr(\"width\");\n" +
                "        var height = \$(this).attr(\"height\");\n" +
                "        var htmlStr='<embed'+\n" +
                "            ' src=\""+playerUrl+"?vcastr_file='+url+'\"'+\n" +
                "            ' quality=\"high\" allowFullScreen=\"true\"'+\n" +
                "            ' pluginspage=\"http://www.macromedia.com/go/getflashplayer\"'+\n" +
                "            ' type=\"application/x-shockwave-flash\" width=\"'+width+'\" height=\"'+height+'\">'+\n" +
                "            ' </embed>';\n" +
                "        var mp3htmlStr='<embed'+\n" +
                "            ' src=\""+mp3playerUrl+"?soundFile='+url+'\"'+\n" +
                "            ' quality=\"high\" allowFullScreen=\"true\"'+\n" +
                "            ' pluginspage=\"http://www.macromedia.com/go/getflashplayer\"'+\n" +
                "            ' type=\"application/x-shockwave-flash\" width=\"'+width+'\" height=\"'+height+'\">'+\n" +
                "            ' </embed>';\n" +
                "        if(url.toLowerCase().indexOf(\".flv\")>-1){\n" +
                "            \$(this).replaceWith(htmlStr);\n" +
                "        }else if(url.toLowerCase().indexOf(\".mp3\")>-1){\n" +
                "            \$(this).replaceWith(mp3htmlStr);\n" +
                "        }\n" +
                "    });\n" +
                "});" +
                "</script>";
        writer<<htmlStr;
    }
}
