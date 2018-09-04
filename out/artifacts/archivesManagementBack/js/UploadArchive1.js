var Main = {
    data() {
        return {
            data: []
        }
    },
    mounted: function () {
        console.log("数据加载")
        this.getWXTreeNode();
    },
    methods: {
        getWXTreeNode() {
            callAxiosGetNoParams("/archivesManagementBack/getCascaderData.do", this.getWXTreeNodeSuc, this.Fail);
        },
        getWXTreeNodeSuc(data) {
            this.data = data;
        },
        Fail(data) {
            console.log(data);
        },
        getKindData(value) {
            // 解析带参页面携带的参数
            function getQueryString(name) {
                var result = window.location.search.match(new RegExp("[\?\&]" + name + "=([^\&]+)", "i"));
                console.log(result);
                if (result == null || result.length < 1) {
                    return "";
                }
                return result[1];
            }
            var Id = getQueryString("Id");
            console.log(Id);
            var data={
                url : value,
                userId : Id
            }
            callAxiosPost("/archivesManagementBack/putArchiveUrl.do", data, this.WXTreeNodeSuc, this.Fail);
        },
        WXTreeNodeSuc(data) {

        },
        upArchiveSuc(response) {
            console.log(response);
            this.$Message.success('上传成功！');

        }
    }
}

var Component = Vue.extend(Main)
new Component().$mount('#app')
