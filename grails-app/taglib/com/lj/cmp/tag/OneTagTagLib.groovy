package com.lj.cmp.tag

import com.lj.cmp.util.customenum.ResourceType
import com.lj.cmp.data.ResourceFileInfo
import org.springframework.web.servlet.support.RequestContextUtils
import com.lj.cmp.util.common.Number

class OneTagTagLib {
    static namespace = "lj";

    Closure paginateInBui = { attrs ->
        def writer = out
        if (attrs.total == null) {
            throwTagError("Tag [paginate] is missing required attribute [total]")
        }

        def messageSource = grailsAttributes.messageSource
        def locale = RequestContextUtils.getLocale(request)

        def total = attrs.int('total') ?: 0
        def offset = params.int('offset') ?: 0
        def max = params.int('max')
        def maxsteps = (attrs.int('maxsteps') ?: 10)

        if (!offset) offset = (attrs.int('offset') ?: 0)
        if (!max) max = (attrs.int('max') ?: 10)

        def linkParams = [:]
        if (attrs.params) linkParams.putAll(attrs.params)
        linkParams.offset = offset - max
        linkParams.max = max
        if (params.sort) linkParams.sort = params.sort
        if (params.order) linkParams.order = params.order

        def linkTagAttrs = [:]
        def action
        if (attrs.containsKey('mapping')) {
            linkTagAttrs.mapping = attrs.mapping
            action = attrs.action
        } else {
            action = attrs.action ?: params.action
        }
        if (action) {
            linkTagAttrs.action = action
        }
        if (attrs.controller) {
            linkTagAttrs.controller = attrs.controller
        }
        if (attrs.id != null) {
            linkTagAttrs.id = attrs.id
        }
        if (attrs.fragment != null) {
            linkTagAttrs.fragment = attrs.fragment
        }
        linkTagAttrs.params = linkParams

        // determine paging variables
        def steps = maxsteps > 0
        int currentstep = (offset / max) + 1
        int firststep = 1
        int laststep = Math.round(Math.ceil(total / max))

        // display previous link when not on firststep unless omitPrev is true
        if (currentstep > firststep && !attrs.boolean('omitPrev')) {
            linkTagAttrs.class = ''
            linkParams.offset = offset - max
            writer << "<li>"
            writer << link(linkTagAttrs.clone()) {
                (attrs.prev ?: messageSource.getMessage('paginate.prev', null, messageSource.getMessage('default.paginate.prev', null, 'Previous', locale), locale))
            }
            writer << "</li>"
        }

        // display steps when steps are enabled and laststep is not firststep
        if (steps && laststep > firststep) {
            linkTagAttrs.class = ''

            // determine begin and endstep paging variables
            int beginstep = currentstep - Math.round(maxsteps / 2) + (maxsteps % 2)
            int endstep = currentstep + Math.round(maxsteps / 2) - 1

            if (beginstep < firststep) {
                beginstep = firststep
                endstep = maxsteps
            }
            if (endstep > laststep) {
                beginstep = laststep - maxsteps + 1
                if (beginstep < firststep) {
                    beginstep = firststep
                }
                endstep = laststep
            }

            // display firststep link when beginstep is not firststep
            if (beginstep > firststep && !attrs.boolean('omitFirst')) {
                linkParams.offset = 0
                writer << "<li>"
                writer << link(linkTagAttrs.clone()) { firststep.toString() }
                writer << "</li>"
            }
            //show a gap if beginstep isn't immediately after firststep, and if were not omitting first or rev
            if (beginstep > firststep + 1 && (!attrs.boolean('omitFirst') || !attrs.boolean('omitPrev'))) {
                writer << "<li>"
                writer << '<a herf="#">..</a>'
                writer << "</li>"
            }

            // display paginate steps
            (beginstep..endstep).each { i ->
                if (currentstep == i) {
                    writer << "<li class=\"active\"><a href=\"#\">${i}</a></li>"
                } else {
                    linkParams.offset = (i - 1) * max
                    writer << link(linkTagAttrs.clone()) { i.toString() }
                }
            }

            //show a gap if beginstep isn't immediately before firststep, and if were not omitting first or rev
            if (endstep + 1 < laststep && (!attrs.boolean('omitLast') || !attrs.boolean('omitNext'))) {
                writer << "<li>"
                writer << '<a href="#">..</a>'
                writer << "</li>"
            }
            // display laststep link when endstep is not laststep
            if (endstep < laststep && !attrs.boolean('omitLast')) {
                linkParams.offset = (laststep - 1) * max
                writer << "<li>"
                writer << link(linkTagAttrs.clone()) { laststep.toString() }
                writer << "</li>"
            }
        }

        // display next link when not on laststep unless omitNext is true
        if (currentstep < laststep && !attrs.boolean('omitNext')) {
            linkTagAttrs.class = ''
            linkParams.offset = offset + max
            writer << "<li>"
            writer << link(linkTagAttrs.clone()) {
                (attrs.next ? attrs.next : messageSource.getMessage('paginate.next', null, messageSource.getMessage('default.paginate.next', null, 'Next', locale), locale))
            }
            writer << "</li>"
        }
    }

    Closure paginateFront = { attrs ->
        def writer = out
        if (attrs.total == null) {
            throwTagError("Tag [paginate] is missing required attribute [total]")
        }

        def messageSource = grailsAttributes.messageSource
        def locale = RequestContextUtils.getLocale(request)

        def total = attrs.int('total') ?: 0
        def offset = params.int('offset') ?: 0
        def max = params.int('max')
        def maxsteps = (attrs.int('maxsteps') ?: 10)

        if (!offset) offset = (attrs.int('offset') ?: 0)
        if (!max) max = (attrs.int('max') ?: 10)

        def linkParams = [:]
        if (attrs.params) linkParams.putAll(attrs.params)
        linkParams.offset = offset - max
        linkParams.max = max
        if (params.sort) linkParams.sort = params.sort
        if (params.order) linkParams.order = params.order

        def linkTagAttrs = [:]
        def action
        if (attrs.containsKey('mapping')) {
            linkTagAttrs.mapping = attrs.mapping
            action = attrs.action
        } else {
            action = attrs.action ?: params.action
        }
        if (action) {
            linkTagAttrs.action = action
        }
        if (attrs.controller) {
            linkTagAttrs.controller = attrs.controller
        }
        if (attrs.id != null) {
            linkTagAttrs.id = attrs.id
        }
        if (attrs.fragment != null) {
            linkTagAttrs.fragment = attrs.fragment
        }
        linkTagAttrs.params = linkParams

        // determine paging variables
        def steps = maxsteps > 0
        int currentstep = (offset / max) + 1
        int firststep = 1
        int laststep = Math.round(Math.ceil(total / max))

        writer << '<div class="pages"><span>共<font>'+total+'</font>条记录</span>'
        // display previous link when not on firststep unless omitPrev is true
        if (currentstep > firststep && !attrs.boolean('omitPrev')) {
            linkTagAttrs.class = ''
            linkParams.offset = offset - max
            writer << link(linkTagAttrs.clone()) {
                (attrs.prev ?: messageSource.getMessage('paginate.prev', null, messageSource.getMessage('default.paginate.prev', null, 'Previous', locale), locale))
            }
        }

        // display steps when steps are enabled and laststep is not firststep
        if (steps && laststep > firststep) {
            linkTagAttrs.class = ''

            // determine begin and endstep paging variables
            int beginstep = currentstep - Math.round(maxsteps / 2) + (maxsteps % 2)
            int endstep = currentstep + Math.round(maxsteps / 2) - 1

            if (beginstep < firststep) {
                beginstep = firststep
                endstep = maxsteps
            }
            if (endstep > laststep) {
                beginstep = laststep - maxsteps + 1
                if (beginstep < firststep) {
                    beginstep = firststep
                }
                endstep = laststep
            }

            // display firststep link when beginstep is not firststep
            if (beginstep > firststep && !attrs.boolean('omitFirst')) {
                linkParams.offset = 0
                writer << link(linkTagAttrs.clone()) { firststep.toString() }
            }
            //show a gap if beginstep isn't immediately after firststep, and if were not omitting first or rev
            if (beginstep > firststep + 1 && (!attrs.boolean('omitFirst') || !attrs.boolean('omitPrev'))) {
                writer << "..."
            }

            // display paginate steps
            (beginstep..endstep).each { i ->
                if (currentstep == i) {
                    writer << "<b>${i}</b>"
                } else {
                    linkParams.offset = (i - 1) * max
                    writer << link(linkTagAttrs.clone()) { i.toString() }
                }
            }

            //show a gap if beginstep isn't immediately before firststep, and if were not omitting first or rev
            if (endstep + 1 < laststep && (!attrs.boolean('omitLast') || !attrs.boolean('omitNext'))) {
                writer << '...'
            }
            // display laststep link when endstep is not laststep
            if (endstep < laststep && !attrs.boolean('omitLast')) {
                linkParams.offset = (laststep - 1) * max
                writer << link(linkTagAttrs.clone()) { laststep.toString() }
            }
        }

        // display next link when not on laststep unless omitNext is true
        if (currentstep < laststep && !attrs.boolean('omitNext')) {
            linkTagAttrs.class = ''
            linkParams.offset = offset + max
            writer << link(linkTagAttrs.clone()) {
                (attrs.next ? attrs.next : messageSource.getMessage('paginate.next', null, messageSource.getMessage('default.paginate.next', null, 'Next', locale), locale))
            }
        }

        writer << '</div>'
    }

    Closure resourceFileUrl = { attrs ->
        def writer = out
        String fileFullName = attrs.fileFullName;
        if (fileFullName == null) {
            throwTagError("Tag [paginate] is missing required attribute [total]")
        }
        String urlStr = request.getContextPath() + "/uploadFile/" + fileFullName;
        writer << urlStr;
    }
    Closure resourceFilePreview = { attrs ->
        def writer = out
        String htmlStr = "";
        long id = Number.toLong(attrs.id);
        int width = Number.toInteger(attrs.width);
        if (width == 0) {
            width = 100;
        }
        int height = Number.toInteger(attrs.height);
        if (height == 0) {
            height = 100;
        }
        if (attrs.id == null) {
            throwTagError("Tag [paginate] is missing required attribute [total]")
        }
        ResourceFileInfo resourceFileInfo = ResourceFileInfo.get(id);
        if (resourceFileInfo) {
            if (resourceFileInfo.type == ResourceType.IMAGE.code) {
                htmlStr = "<img src='" + request.getContextPath() + "/uploadFile/" + resourceFileInfo.fileFullName + "' width='" + width + "' height='" + height + "'/>";
            } else if (resourceFileInfo.type == ResourceType.FLASH.code||resourceFileInfo.type == ResourceType.MEDIA.code) {
                String contentStr = "<iframe id=\"iframe" + resourceFileInfo.id + "\" src=\"" + createLink(controller: "resourceFile", action: "previewFlash") + "?id=" + resourceFileInfo.id + "\"" +
                        "width=500 height=300></iframe>";
                htmlStr = "<input type='button' id=\"btnShow" + resourceFileInfo.id + "\" value='预览'/>" +
                        "<script type=\"text/javascript\">\n" +
                        " BUI.use('bui/overlay',function(Overlay){\n" +
                        "     \$('#btnShow" + resourceFileInfo.id + "').on('click',function () {\n" +
                        "           var dialog" + resourceFileInfo.id + " = new Overlay.Dialog({\n" +
                        "               title:'预览-" + resourceFileInfo.fileName + "',\n" +
                        "               width:550,\n" +
                        "               height:360,\n" +
                        "               mask:false,\n" +
                        "               closeAction : 'destroy', //每次关闭dialog释放\n" +
                        "               buttons:[],\n" +
                        "               bodyContent:'" + contentStr + "'\n" +
                        "           });\n" +
                        "          dialog" + resourceFileInfo.id + ".show();\n" +
                        "     });\n" +
                        " });\n" +
                        " </script>";
            } else {
                htmlStr = "<a href='" + request.getContextPath() + "/uploadFile/" + resourceFileInfo.fileFullName + "'>" + resourceFileInfo.fileName + "</a>";
            }
        }
        writer << htmlStr;
    }
}
