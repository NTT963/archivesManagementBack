var Main = {
    data() {
        return {
            treeData: [],
            columns: [
                // {
                //     type: 'selection',
                //     width: 55,
                //     align: 'center'
                // },
                {
                    title: '档案名',
                    key: 'archivesName',
                    width: 400
                },
                {
                    title: '上传时间',
                    key: 'uploadTime',
                    width: 170
                },
                {
                    title: '档案摘要',
                    key: 'content',
                },
                // {
                //         title: '档案等级',
                //         key: 'authority',
                //         width: '150'
                //       },

                {
                    title: '操作',
                    key: 'action',
                    width: 200,
                    align: 'center',
                    render: (h, params) => {
                        return h('div', [
                            h('Button', {
                                props: {
                                    type: 'success',
                                    size: 'small'
                                },
                                style: {
                                    marginRight: '15px'
                                },
                                on: {
                                    click: () => {
                                        // alert(JSON.stringify(params.row.url + params.row.archivesId))
                                        var url = params.row.url + '/' + params.row.archivesId
                                        // alert(url)
                                        this.itemPreview(url)
                                        this.change(params.index)
                                    }
                                }
                            }, '在线查看'),
                            h('Button', {
                                props: {
                                    type: 'warning',
                                    size: 'small'
                                },
                                on: {
                                    click: () => {
                                        this.remove(params.index)
                                    }
                                }
                            }, '下载文件')
                        ]);
                    }
                }
            ],
            data: [],
            pageSize: 10,
            pageSizeArray: [5, 10, 15],
            currentPage: 1,
            dataCount: 0,
            nowData: [],
            loading: true,
            tableTitle: '请点选择左侧目录',
            tableClassifyId: '',
            modal: false,
            formItem: {
                archivesId: '',
                archivesName: '',
                authority: ''
            },
            modelTitle: '新建档案',
            classifyId: '',
            WXTreeData: [],
            imgList: [],
            size: 0,
            userId: '',
            cascaderData: [],
            node: null,
            num: 0,
            array: []
        }
    },
    mounted: function () {
        // 向后台发送请求所有节点数据，并对data初始化
        callAxiosGetNoParams("/archivesManagementBack/getAllTreeNode.do", this.moutedSuccess, this.moutedFailed)
        this.getWXTreeNode()
        this.getKindData();
        /*callAxiosGetNoParams("/archivesManagementBack/getArchivesByClassifyId.do?classifyId=JXB02",this.testSuc,this.testFail)
        callAxiosGet("/archivesManagementBack/getArchivesByClassifyId.do", {'classifyId': 'JXB02'}, this.testSuc,this.testFail)
        callAxiosGet("/archivesManagementBack/getArchivesByClassifyId.do", {'classifyId': 'JXB02'}, this.testSuc,this.testFail)
    */
    },
    methods: {
        fileClick() {
            document.getElementById('file').click()
        },
        getCookie(name) {
            var arr = document.cookie.match(new RegExp("(^| )" + name + "=([^;]*)(;|$)"));
            if (arr != null) {
                return unescape(arr[2]);
            } else {
                return null;
            }
        },
        getKindData() {

            this.userId = this.getCookie("userId");
            // console.log("获得的用户ID为"+Id);
        },
        getWXTreeNode() {
            callAxiosGetNoParams("/archivesManagementBack/getCascaderData.do", this.getWXTreeNodeSuc, this.Fail);
        },
        getWXTreeNodeSuc(data) {
            this.WXTreeData = data;
        },
        Fail(data) {
            console.log(data);
        },

        itemPreview(url) {
            callAxiosGet("/archivesManagementBack/fileHandle/convertOfficeToPDF.do", {
                officePath: url
            }, this.convertOfficeToPDFSuc, this.convertOfficeToPDFFail)
            // alert(url)
        },
        convertOfficeToPDFSuc(data) {
            // page/FileExplorePage.html?url=/archive/JXB/JXB02/test/软件工程专业卓越工程师教育培养方案文字说明.pdf
            var previewURL = encodeURI("FileExplorePage.html?url=" + data.previewURL);   //使用encodeURI编码
            // window.location.href =previewURL;
            window.open(previewURL);
            // alert(data.pdfURL)

        },
        convertOfficeToPDFFail(data) {
        },
        chooseSuc(data) {
            // console.log("查到的档案信息为==>" + JSON.stringify(data))
            // this.tableTitle = this.tableTitle + data
            data = this.zipContent(data)
            this.currentPage = 1
            if (data < this.pageSize) {
                this.nowData = data
            } else {
                this.nowData = data.slice(0, this.pageSize)
                this.dataCount = data.length
            }
            this.data = data
        },
        zipContent(data) {
            for (var i = 0, len = data.length; i < len; i++) {
                console.log(data[i].content !== null)
                if (data[i].content !== null) {
                    if (data[i].content.length > 100) {
                        data[i].content = data[i].content.substring(0, 100) + " ... ..."
                    } else {
                        console.log(data[i].content)
                    }
                }
                // if(data[i].content === null){}
                // console.log(data[i].content.length>130)
                //     if(data[i].content.length > 130){
                // //         // data[i].content = data[i].content.substring(0,120)+" ... ..."
                // //         console.log(data[i].content)
                //     }else {
                // //         console.log(data[i].content)
                //     }
                //
                //     // console.log("===>"+data[i].content.substring(0,100))
            }
            return data

        },
        chooseFail(data) {
            console.log("请求失败" + data)
        },
        testSuc(data) {
            console.log("test查到的档案信息为==>" + JSON.stringify(data))
        },
        testFail(data) {
            console.log("请求档案失败")
        },
        moutedSuccess(data) {
            this.treeData = data
        },

        moutedFailed(data) {
            console.log("初始化失败")

        },

        modelOK() {
            var archive = {}
            archive.classifyId = this.classifyId
            archive.archivesId = this.classifyId + this.formItem.archivesId
            archive.archivesName = this.formItem.archivesName
            archive.authority = this.formItem.authority

            this.axiosPost('http://localhost:8070/addArchive', archive)

            this.nowData.push(archive)

            console.log("新建档案" + JSON.stringify(archive))
        },
        handleSelectAll(status) {
            this.$refs.selection.selectAll(status);
        },
        addArchives() {
            this.modal = true

        },
        change(index) {
            index = index + this.pageSize * (this.currentPage - 1)
            console.log(index)
        },
        insert(index) {
            index = index + this.pageSize * (this.currentPage - 1)

        },
        remove(index) {
            index = index + this.pageSize * (this.currentPage - 1)

        },
        changePage(page) {
            console.log(page)
            var start = (page - 1) * this.pageSize;
            var end = page * this.pageSize;
            this.nowData = this.data.slice(start, end)
            this.currentPage = page
        },
        pageSizeChange(size) {
            this.pageSize = size
            var start = (this.currentPage - 1) * this.pageSize;
            var end = this.currentPage * this.pageSize;
            this.nowData = this.data.slice(start, end)
            console.log(size)

        },
        renderContent(h, {root, node, data}) {
            return h('span', {
                style: {
                    display: 'inline',
                }
            }, [
                h('span', [
                    h('Icon', {
                        props: {
                            type: 'folder',
                            size: '20',
                            color: '#F6AF39'
                        },
                        style: {
                            marginRight: '8px',
                        }
                    }),
                    h('a', {
                        style: {
                            fontSize: '14px',
                            color: '#000',
                            display: 'inline-block',
                            verticalAlign: 'middle',
                            // minWidth: '400px',
                            // backgroundColor: '#F6AF39',
                            //width: '300px',
                            overflow: 'hidden',
                            whitespace: 'nowrap',
                            textOverflow: 'ellipsis',
                        },
                        on: {
                            click: () => {
                                this.choose(root, node, data)
                            }
                        }
                    }, data.title)
                ])
            ]);

        },


        choose(root, node, data) {
            this.tableClassifyId = data.classifyId
            this.tableTitle = data.title + "(" + this.tableClassifyId + ")";
            // console.log(data.classifyId)
            if (data.children !== null) {
                console.log("不是叶子")
                this.nowData = []
            } else {
                console.log("是叶子")
                callAxiosGet("/archivesManagementBack/getArchivesByClassifyId.do", {'classifyId': data.classifyId}, this.chooseSuc, this.chooseFail)
                this.node = null;
                this.num = 0;
                this.array = []
                // alert(node + num + array)
                this.getArray(this.treeData, data.classifyId)
                this.cascaderData = this.array

                // alert(JSON.stringify(array))
            }


        },
        getArray(data, name) {
            // alert("调用")
            for (let i in data) {
                if (this.node) {
                    console.log("终止递归")
                    break;
                }
                if (data[i].classifyFatherId == 0) {
                    console.log("===========================")
                    this.num = 0
                }

                if (data[i].classifyFatherId == 0 || data[i].children !== null) {
                    console.log(this.num + ":" + data[i].classifyId)
                    this.array[this.num] = data[i].classifyId
                    this.num++;
                }

                if (data[i].classifyId === name) {
                    this.array[this.num] = data[i].classifyId
                    console.log(this.num + ":" + data[i].classifyId)
                    console.log("找到了" + JSON.stringify(this.array))
                    this.node = data[i]
                } else {

                    if (data[i].children) {
                        this.getArray(data[i].children, name);
                    } else {
                        continue
                    }
                }

            }
        }
    }
}

var Component = Vue.extend(Main)
new Component().$mount('#app')


// function getArray(data, name) {
//     // alert("调用")
//     for (let i in data) {
//         if (this.node) {
//             console.log("终止递归")
//             break;
//         }
//         if (data[i].classifyFatherId == 0) {
//             console.log("===========================")
//             num = 0
//         }
//
//         if (data[i].classifyFatherId == 0 || data[i].children !== null) {
//             console.log(num + ":" + data[i].classifyId)
//             array[num] = data[i].classifyId
//             num++;
//         }
//
//         if (data[i].classifyId === name) {
//             array[num] = data[i].classifyId
//             console.log(num + ":" + data[i].classifyId)
//             console.log("找到了" + JSON.stringify(array))
//             node = data[i]
//         } else {
//
//             if (data[i].children) {
//                 getArray(data[i].children, name);
//             } else {
//                 continue
//             }
//         }
//
//     }
// }