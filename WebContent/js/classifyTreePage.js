var Main = {
    data() {
        return {
            treeData: [],
            switch1: false,
            state: 'detail',
            admin: {
                "1": [],
                "2": [],
                "3": []
            },

            teacherList: [],
            admin1: [],
            admin2: [],
            admin3: [],

            formItem: {
                ID:'',
                classifyId: '1',
                title: '2',
                admin: {},
                classifyLevel: '1111',
                fatherData: '111',
                editNodeDate: '111',
                addIsRoot: false
            },

            modelState: 'edit',
            isEdit: false,
            buttonProps: {
                type: 'ghost',
                size: 'small',
            },
            modalEdit: false,
            modalAdd: false,
            modalDetail: false,
            modelTitle: '新建目录'
        }
    },
    mounted: function () {
        //向后台发送请求所有节点数据，并对data初始化
        callAxiosGetNoParams("/archivesManagementBack/getAllTreeNode.do", this.moutedSuccess, this.moutedFailed)
    },
    methods: {
        clearForm() {
            this.formItem.ID = '';
            this.formItem.classifyId = '';
            this.formItem.title = '';
            this.formItem.admin = {}

        },
        handleAdd() {
            if (this.count.length) {
                this.count.push(this.count[this.count.length - 1] + 1);
            } else {
                this.count.push(0);
            }
        },
        tagClosePre(event, name) {
            console.log("要删除的为"+name + this.formItem.ID)
            this.$Modal.confirm({
                title: '删除分管领导',
                content: '<p>确定删除该分管领导?</p><p>删除后不可恢复</p>',
                onOk: () => {
                    const index = this.admin1.indexOf(name);
                    this.admin1.splice(index, 1);
                    this.$Message.info('分管领导已经删除');
                },
                onCancel: () => {
                    this.$Message.info('取消删除');
                }
            });
        },
        tagCloseExtra(event, name) {
            console.log("要删除的为"+name)
            this.$Modal.confirm({
                title: '删除分管领导',
                content: '<p>确定删除该负责人？</p><p>删除后不可恢复</p>',
                onOk: () => {
                    const index = this.admin1.indexOf(name);
                    this.admin1.splice(index, 1);
                    this.$Message.info('负责人已经删除');
                },
                onCancel: () => {
                    this.$Message.info('取消删除');
                }
            });
        },
        handleClose1(event, name) {
            this.$Modal.confirm({
                title: '删除责任人',
                content: '<p>确定删除该责任人？</p><p>删除后不可恢复</p>',
                onOk: () => {
                    const index = this.admin2.indexOf(name);
                    this.admin2.splice(index, 1);
                    this.$Message.info('责任人已删除');
                },
                onCancel: () => {
                    this.$Message.info('取消删除');
                }
            });

        },
        handleClose2(event, name) {
            this.$Modal.confirm({
                title: '删除责任人',
                content: '<p>确定删除该责任人？</p><p>删除后不可恢复</p>',
                onOk: () => {
                    const index = this.admin2.indexOf(name);
                    this.admin2.splice(index, 1);
                    this.$Message.info('责任人已删除');
                },
                onCancel: () => {
                    this.$Message.info('取消删除');
                }
            });

        },
        handleClose3(event, name) {
            this.$Modal.confirm({
                title: '删除协助人',
                content: '<p>确定删除该协助人？</p><p>删除后不可恢复</p>',
                onOk: () => {
                    this.$Message.info('协助人已删除');
                },
                onCancel: () => {
                    this.$Message.info('取消删除');
                }
            });
            const index = this.admin3.indexOf(name);
            this.admin3.splice(index, 1);

        },
        change(status) {
            if (status)
                this.state = 'edit'
            else
                this.state = 'detail'

            this.$Message.info('开关状态：' + status + this.state);
        },

        moutedSuccess(data) {
            this.treeData = data
        },

        moutedFailed(data) {

        },

        getTeacherSuc(data){
            var listData = []
            data.forEach(function(e) {
                var item = {}
                item.value = e
                item.label = e
                listData.push(item)
            })
            console.log(listData)
            this.teacherList = listData

        },
        fail(data){

        },
        //添加根节点
        addRoot() {
            callAxiosGetNoParams("/archivesManagementBack/getAllTeacher.do",this.getTeacherSuc,this.fail)
            this.clearForm()
            this.modalAdd = true
            this.addIsRoot = true

        },

        getEditInfoSuc(data){
            this.formItem.admin = data
            console.log(JSON.stringify(data))
            // this.admin1 = data['1'];
            // this.admin2 = data['2'];
            // this.admin3 = data['3'];
        },

        getDetailSuc(data) {
            console.log(JSON.stringify(data))
            this.formItem.admin = data
        },
        getDetailFail(data) {

        },

        //弹窗的确认事件
        modelAddOK() {
            this.append(this.addIsRoot)
        },
        modelDetailOK(){

        },
        modelEditOK(){

        },
        //通过render函数自定义树的样式及点击事件
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
                            fontSize: '16px',
                            color: '#000',
                            display: 'inline-block',
                            verticalAlign: 'middle',
                            minWidth: '500px',
                            // backgroundColor: '#F6AF39',
                            // width: '430px',
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
                ]),
                h('span', {
                    style: {
                        float: 'right',
                        marginRight: '90px'
                    }
                }, [
                    h('Tooltip', {
                        props: {
                            content: "目录详情",
                            placement: "top"
                        }
                    }, [h('Button', {
                        props: Object.assign({}, this.buttonProps, {
                            icon: 'more'
                        }), style: {
                            marginRight: '8px'
                        },
                        on: {
                            click: () => {
                                if(data.children == null){
                                    callAxiosGet("/archivesManagementBack/getTreeNodeDetail.do", {id: data.iD}, this.getDetailSuc, this.getDetailFail)
                                }else {
                                    this.formItem.admin = {}
                                }
                                this.formItem.ID = data.ID
                                this.formItem.classifyId = data.classifyId
                                this.formItem.title = data.title
                                this.modalDetail = true
                            }
                        }
                    })]),
                    h('Tooltip', {
                        props: {
                            content: "修改目录信息",
                            placement: "top"
                        }
                    }, [h('Button', {
                        props: Object.assign({}, this.buttonProps, {
                            icon: 'edit'
                        }), style: {
                            marginRight: '8px'
                        },
                        on: {
                            click: () => {
                                if(data.children == null){
                                    callAxiosGet("/archivesManagementBack/getTreeNodeDetail.do", {id: data.iD}, this.getDetailSuc, this.getDetailFail)
                                }else {
                                    this.formItem.admin = {}
                                }

                                // if(data.children == null){
                                //     callAxiosGet("/archivesManagementBack/getTreeNodeDetail.do", {id: data.iD}, this.getEditInfoSuc, this.getDetailFail)
                                // }else {
                                //     this.formItem.admin = {}
                                // }
                                callAxiosGetNoParams("/archivesManagementBack/getAllTeacher.do",this.getTeacherSuc,this.fail)
                                this.formItem.ID = data.iD
                                console.log("ID为：" + JSON.stringify(data)+ data.ID + data.classifyId)
                                this.formItem.classifyId = data.classifyId
                                this.formItem.title = data.title
                                this.modalEdit = true
                            }
                        }
                    }),]),
                    h('Tooltip', {
                        props: {
                            content: "添加子目录",
                            placement: "top"
                        }
                    }, [h('Button', {
                        props: Object.assign({}, this.buttonProps, {
                            icon: 'plus-round',
                        }),
                        style: {
                            marginRight: '8px'
                        },
                        on: {
                            click: () => {
                                callAxiosGetNoParams("/archivesManagementBack/getAllTeacher.do",this.getTeacherSuc,this.fail)
                                this.addIsRoot = false
                                this.clearForm()
                                this.modalAdd = true
                                this.formItem.fatherData = data
                            }
                        }
                    })]),

                    h('Tooltip', {
                        props: {
                            content: "删除该目录",
                            placement: "top"
                        }
                    }, [h('Button', {
                        props: Object.assign({}, this.buttonProps, {
                            icon: 'trash-a'
                        }),
                        on: {
                            click: () => {
                                //
                                // console.log("root-->" + JSON.stringify(root))
                                // console.log("node-->" + JSON.stringify(node))
                                // console.log("data-->" + JSON.stringify(data))
                                this.remove(root, node, data)
                            }
                        }
                    })])
                ])
            ]);
        },

        selectChanged(data){
            console.log("选中了"+JSON.stringify(data))

        },
        //增加树节点
        append(isRoot) {
            const data = this.formItem.fatherData
            const children = data.children || [];

            var treeNode = {}
            treeNode.classifyId = this.formItem.classifyId
            treeNode.title = this.formItem.title
            treeNode.children = null

            var admins = []
            this.admin1.forEach(function(e) {
                admins.push({"1":e.substring(0,e.indexOf('-'))})
            })
            this.admin2.forEach(function(e) {
                admins.push({"2":e.substring(0,e.indexOf('-'))})
            })
            this.admin3.forEach(function(e) {
                admins.push({"3":e.substring(0,e.indexOf('-'))})
            })
            var params = {treNode:treeNode,admin:admins}
            callAxiosPost("/archivesManagementBack/addTreeNode.do",params,this.addNodeSuc,this.fail)
            console.log("取到回掉ID"+this.formItem.ID)
            if (isRoot) {
                treeNode.classifyFatherId = 0
                this.treeData.push(treeNode)
                console.log("========》添加根节点")
            } else {
                treeNode.classifyFatherId = data.classifyId
                children.push(treeNode)
                this.$set(data, 'children', children);

            }


            console.log(JSON.stringify(params))


            // callAxiosPostNoFun("/archivesManagementBack/addTreeNode.do", params)

            this.$Message.info('节点添加成功');
        },

        addNodeSuc(data){
            location.reload()
            location.re
        },
        //编辑树节点
        edit() {
            const editNodeData = this.formItem.editNodeDate
            console.log("修改方法" + JSON.stringify(editNodeData))


            var admins = []
            this.admin1.forEach(function(e) {
                admins.push({"1":e.substring(0,e.indexOf('-'))})
            })
            this.admin2.forEach(function(e) {
                admins.push({"2":e.substring(0,e.indexOf('-'))})
            })
            this.admin3.forEach(function(e) {
                admins.push({"3":e.substring(0,e.indexOf('-'))})
            })

            var treeNode = {}
            treeNode.classifyId = this.formItem.classifyId
            treeNode.title = this.formItem.title
            treeNode.classifyLevel = this.formItem.classifyLevel

            this.$set(editNodeData, 'classifyFatherId', this.formItem.classifyId);
            this.$set(editNodeData, 'title', this.formItem.title);
            this.$set(editNodeData, 'classifyLevel', this.formItem.classifyLevel);
            callAxiosPostNoFun('/archivesManagementBack/updateTreeNode', treeNode)

            // this.axiosPost('/updateTreeNode', treeNode)
        },
        //点击树节点
        choose(root, node, data) {
            console.log("点击节点" + JSON.stringify(data.title))
            // const parentKey = root.find(el => el === node).parent;
            // const parent = root.find(el => el.nodeKey === parentKey).node;
            // const index = parent.children.indexOf(data);

            // this.axios.get(ip + '/getArchivesByClassifyId', {
            //     params: {'classifyId': data.classifyId}
            // }).then(function (response) {
            //     var transformData = {}
            //     transformData.tableData = response.data
            //     transformData.treeNode = data
            //     bus.$emit("updateEvent", transformData)
            // }).catch(function (error) {
            //     alert(error);
            // });

        },
        //删除树节点
        remove(root, node, data) {
            var index, parent;
            if (data.classifyFatherId == 0) {
                console.log("节点信息" + JSON.stringify(data))
                // this.treeData.push(treeNode2)
                index = this.treeData.indexOf(data)
                // index = root.indexOf(node)
                console.log("根节点编号" + index)
                parent = root

                // console.log(JSON.stringify(this.treeData))
                // console.log("删除的是根节点,第" + node.nodeKey + "个")
                console.log("根节点!!" + JSON.stringify(root.find(el => el === node)))

            } else {
                const parentKey = root.find(el => el === node).parent;
                parent = root.find(el => el.nodeKey === parentKey).node;
                index = parent.children.indexOf(data);

            }
            console.log("前端序号" + index)


            this.$Modal.confirm({
                title: '删除节点',
                content: '<p>确定删除此节点嘛</p><p>节点删除后，子目录也会被删除</p>',
                onOk: () => {

                    var params = {classifyId: data.classifyId, ID:data.iD}
                    console.log("要删除的节点为" + data.iD+JSON.stringify(params))
                    callAxiosPostNoFun('/archivesManagementBack/deleteTreeNode.do', params)
                    // callAxiosGetNoFun('/archivesManagementBack/deleteTreeNode.do', params)

                    if (data.classifyFatherId == 0) {

                        this.treeData.splice(index, 1)
                        parent.splice(index, 1);
                        console.log("删除根节点成功" + index)
                        // this.treeData.splice(node.nodeKey, 1)
                        // this.treeData.push()
                        //
                        // root.splice(node.nodeKey, 1)
                    } else {
                        parent.children.splice(index, 1);
                    }
                    this.$Message.info('删除节点成功');

                },
                onCancel: () => {
                    this.$Message.info('取消删除');
                }
            });


        }
    }
}

var Component = Vue.extend(Main)
new Component().$mount('#app')

