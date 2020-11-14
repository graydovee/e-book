(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["form"],{"22fc":function(e,t,r){"use strict";r.r(t);var a=function(){var e=this,t=e.$createElement,r=e._self._c||t;return r("div",[r("div",{staticClass:"crumbs"},[r("el-breadcrumb",{attrs:{separator:"/"}},[r("el-breadcrumb-item",[r("i",{staticClass:"el-icon-lx-calendar"}),e._v(" 爬取\n            ")])],1)],1),r("div",{staticClass:"container"},[r("el-row",[r("el-col",{attrs:{span:3}},[r("el-image",{directives:[{name:"loading",rawName:"v-loading",value:e.image_loading,expression:"image_loading"}],staticClass:"cover",attrs:{src:e.form.coverUrl}},[r("div",{staticClass:"image-slot image-bg",attrs:{slot:"error"},slot:"error"})])],1),r("el-col",{attrs:{span:12}},[r("div",{staticClass:"form-box"},[r("el-form",{ref:"form",attrs:{model:e.form,"label-width":"80px"}},[r("el-form-item",{attrs:{label:"书名"}},[r("el-input",{model:{value:e.form.bookName,callback:function(t){e.$set(e.form,"bookName",t)},expression:"form.bookName"}})],1),r("el-form-item",{attrs:{label:"作者"}},[r("el-input",{model:{value:e.form.authorName,callback:function(t){e.$set(e.form,"authorName",t)},expression:"form.authorName"}})],1),r("el-form-item",{attrs:{label:"简介"}},[r("el-input",{model:{value:e.form.introduce,callback:function(t){e.$set(e.form,"introduce",t)},expression:"form.introduce"}})],1),r("el-form-item",{attrs:{label:"封面地址"}},[r("el-input",{model:{value:e.form.coverUrl,callback:function(t){e.$set(e.form,"coverUrl",t)},expression:"form.coverUrl"}})],1),r("el-form-item",{attrs:{label:"首章地址"}},[r("el-input",{model:{value:e.form.firstChapterUrl,callback:function(t){e.$set(e.form,"firstChapterUrl",t)},expression:"form.firstChapterUrl"}})],1),r("el-form-item",{attrs:{label:"规则"}},[r("el-select",{attrs:{placeholder:"请选择"},model:{value:e.form.matchRexId,callback:function(t){e.$set(e.form,"matchRexId",t)},expression:"form.matchRexId"}},e._l(e.rex,(function(e){return r("el-option",{key:e.id,attrs:{label:e.name,value:e.id}})})),1),r("el-button",{staticClass:"refresh",attrs:{type:"primary",icon:"el-icon-refresh"},on:{click:e.refresh}},[e._v("刷新")])],1),r("el-form-item",[r("el-button",{attrs:{type:"primary"},on:{click:e.onSubmit}},[e._v("开始爬取")]),r("el-button",{on:{click:e.reset}},[e._v("取消")])],1)],1)],1)]),r("el-col",{attrs:{span:6,offset:3}},[r("el-form",[r("el-form-item",{attrs:{label:"自定义爬取:"}},[r("el-switch",{model:{value:e.advance,callback:function(t){e.advance=t},expression:"advance"}})],1),e.advance?r("div",[r("el-form-item",{attrs:{label:"主页地址:"}},[r("el-input",{model:{value:e.url,callback:function(t){e.url=t},expression:"url"}})],1),r("el-form-item",[r("el-button",{attrs:{type:"primary"},on:{click:function(t){return e.getIndex(e.url)}}},[e._v("自动填充")])],1)],1):r("div",[r("el-form-item",{attrs:{label:"书名或作者:"}},[r("el-input",{staticClass:"handle-input mr10",model:{value:e.findStr,callback:function(t){e.findStr=t},expression:"findStr"}})],1),r("el-form-item",[r("el-button",{attrs:{type:"primary",icon:"el-icon-search"},on:{click:e.search}},[e._v("搜索")])],1)],1)],1)],1)],1)],1),e.advance?e._e():r("div",{staticClass:"container"},[r("div",{staticStyle:{margin:"20px"}},[e._v("点击选择小说")]),r("div",e._l(e.books,(function(t){return r("el-button",{staticClass:"chapter",staticStyle:{margin:"0 10px 0 0"},attrs:{title:t.title},on:{click:function(r){return e.choose(t.url)}}},[e._v(e._s(t.title))])})),1)]),e.showChapters?r("div",{staticClass:"container"},[r("div",{staticStyle:{margin:"20px"}},[e._v("点击选择首章")]),r("div",e._l(e.chapters,(function(t){return r("el-button",{staticClass:"chapter",staticStyle:{margin:"0 10px 0 0"},attrs:{title:t.title},on:{click:function(r){return e.setFirstChapter(t.url)}}},[e._v(e._s(t.title))])})),1)]):e._e()])},o=[],i={name:"SpiderForm",data:function(){return{form:{bookName:"",authorName:"",firstChapterUrl:"",coverUrl:"",introduce:"",matchRexId:""},advance:!1,rex:[],findStr:"",books:[],image_loading:!1,url:"",chapters:[],showChapters:!1}},created:function(){this.getRex()},methods:{refresh:function(){var e=this;this.$axios.get("/admin/rex").then((function(t){e.rex=t.data,e.$message.success("刷新成功！")}))},onSubmit:function(){var e=this;this.$axios.post("/admin/book",this.form).then((function(t){200===t.code?(e.$message.success("正在爬取！"),e.reset()):e.$message.error(t.data)}))},getRex:function(){var e=this;this.$axios.get("/admin/rex").then((function(t){e.rex=t.data}))},reset:function(){this.form={bookName:"",authorName:"",firstChapterUrl:"",coverUrl:"",introduce:"",matchRexId:""},this.url="",this.chapters=[],this.showChapters=!1},getIndex:function(e){var t=this;if(e){this.image_loading=!0;var r={url:e};this.$axios.post("/spider/index",r).then((function(e){var r=e.data;t.form.bookName=r.bookName,t.form.authorName=r.authorName,t.form.coverUrl=r.coverUrl,t.form.introduce=r.introduce,t.form.matchRexId=r.matchRexId,t.chapters=r.chapters,t.showChapters=!0,t.image_loading=!1})).catch((function(e){t.$message.error("获取失败"),t.image_loading=!1}))}},setFirstChapter:function(e){this.form.firstChapterUrl=e},search:function(){var e=this,t={name:this.findStr};this.$axios.post("/spider/search",t).then((function(t){e.books=t.data,console.log(t.data)}))},choose:function(e){this.getIndex(e)}}},s=i,n=(r("4994"),r("2877")),l=Object(n["a"])(s,a,o,!1,null,null,null);t["default"]=l.exports},4994:function(e,t,r){"use strict";var a=r("b4c5"),o=r.n(a);o.a},b4c5:function(e,t,r){},c629:function(e,t,r){"use strict";r.r(t);var a=function(){var e=this,t=e.$createElement,r=e._self._c||t;return r("div",[r("div",{staticClass:"crumbs"},[r("el-breadcrumb",{attrs:{separator:"/"}},[r("el-breadcrumb-item",[r("i",{staticClass:"el-icon-lx-calendar"}),e._v(" 规则\n            ")])],1)],1),r("div",{staticClass:"container"},[r("div",{staticClass:"form-box"},[r("el-form",{ref:"form",attrs:{model:e.form,"label-width":"120px"}},[r("el-form-item",{attrs:{label:"名称"}},[r("el-input",{model:{value:e.form.name,callback:function(t){e.$set(e.form,"name",t)},expression:"form.name"}})],1),r("el-form-item",{attrs:{label:"介绍"}},[r("el-input",{model:{value:e.form.info,callback:function(t){e.$set(e.form,"info",t)},expression:"form.info"}})],1),r("el-form-item",{attrs:{label:"内容匹配规则"}},[r("el-input",{model:{value:e.form.contentRex,callback:function(t){e.$set(e.form,"contentRex",t)},expression:"form.contentRex"}})],1),r("el-form-item",{attrs:{label:"标题匹配规则"}},[r("el-input",{model:{value:e.form.titleRex,callback:function(t){e.$set(e.form,"titleRex",t)},expression:"form.titleRex"}})],1),r("el-form-item",{attrs:{label:"下一章匹配规则"}},[r("el-input",{model:{value:e.form.nextPageRex,callback:function(t){e.$set(e.form,"nextPageRex",t)},expression:"form.nextPageRex"}})],1),r("el-form-item",[r("el-button",{attrs:{type:"primary"},on:{click:e.postRex}},[e._v("新增规则")]),r("el-button",{on:{click:e.reset}},[e._v("取消")])],1)],1)],1)])])},o=[],i={name:"RexForm",data:function(){return{form:{}}},created:function(){0},methods:{postRex:function(){var e=this;this.$axios.post("/admin/rex",this.form).then((function(t){e.$message.success("新增成功！"),e.reset()})).catch((function(t){e.$message.error("新增失败！")}))},reset:function(){this.form={contentRex:"",titleRex:"",nextPageRex:"",name:"",info:""}}}},s=i,n=r("2877"),l=Object(n["a"])(s,a,o,!1,null,null,null);t["default"]=l.exports}}]);