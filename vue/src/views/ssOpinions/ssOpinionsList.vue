<template>
    <page-header-wrapper>
        <a-card :bordered="false">
            <div class="table-page-search-wrapper">
                <a-form layout="inline">
                    <a-row :gutter="48">
                        <a-col :md="8" :sm="24">
                            <a-form-item label="名称">
                                <a-input v-model="queryParam.title" placeholder=""/>
                            </a-form-item>
                        </a-col>
                        <a-col :md="8" :sm="24">
                            <a-form-item label="使用状态">
                                <a-select v-model="queryParam.status" placeholder="请选择" default-value="0">
                                    <a-select-option :value="0">全部</a-select-option>
                                    <a-select-option :value="1">未回复</a-select-option>
                                    <a-select-option :value="2">已回复</a-select-option>
                                </a-select>
                            </a-form-item>
                        </a-col>
                        <template v-if="advanced">
                        </template>
                        <a-col :md="!advanced && 8 || 24" :sm="24">
              <span class="table-page-search-submitButtons" :style="advanced && { float: 'right', overflow: 'hidden' } || {} ">
                <a-button type="primary" @click="$refs.table.refresh(true)">查询</a-button>
                <a-button style="margin-left: 8px" @click="resetSearchForm">重置</a-button>
              </span>
                        </a-col>
                    </a-row>
                </a-form>
            </div>

            <div class="table-operator">
                <a-button type="primary" v-action:add icon="plus" @click="handleAdd">新建</a-button>
                <a-dropdown v-action:delete v-if="selectedRowKeys.length > 0">
                    <a-menu slot="overlay">
                        <a-menu-item key="1" @click="handleconfirmDel"><a-icon type="delete" />删除</a-menu-item>
                        <!-- lock | unlock -->
                        <a-menu-item key="2"><a-icon type="lock" />锁定</a-menu-item>
                    </a-menu>
                    <a-button style="margin-left: 8px">
                        批量操作 <a-icon type="down" />
                    </a-button>
                </a-dropdown>
            </div>

            <s-table
                    ref="table"
                    size="default"
                    rowKey="id"
                    :columns="columns"
                    :data="loadData"
                    :alert="false"
                    :rowSelection="rowSelection"
                    showPagination="auto"
            >
        <span slot="serial" slot-scope="text, record, index">
          {{ index + 1 }}
        </span>
                <span slot="status" slot-scope="text">
          <a-badge :status="text | statusTypeFilter" :text="text | statusFilter" />
        </span>
        <span slot="action" slot-scope="text, record">
          <template>
            <!-- <a v-action:edit @click="handleEdit(record)">修改</a> -->
             <a  @click="handleEdit(record)">回复</a>
            <!-- <a-divider type="vertical" /> -->
             <!-- <a  @click="handleTable(record)">回复查看</a> -->
            <a-popconfirm title="是否要删除当前数据？" @confirm="handleDel(record)">
              <a v-action:delete style="color: red">删除</a>
            </a-popconfirm>
          </template>
        </span>
            </s-table>

            <edit-form
                    ref="editForm"
                    :title="title"
                    :visible="visible"
                    :loading="confirmLoading"
                    :model="mdl"
                    @cancel="handleCancel"
                    @ok="handleOk"
            />
            <Etable 
                    ref="etable"
                    :title="title"
                    :visible="tableVisible"
                    :loading="confirmLoading"
                    :model="mdl"
                    @cancel="handleCancel1"
                    @ok="handleOk1"
            />
        </a-card>
    </page-header-wrapper>
</template>

<script>
    import moment from 'moment'
    import { STable, Ellipsis } from '@/components'
    import { statusMap } from '@/api/RC'
    import { getSsOpinionsList, saveSsOpinions, delSsOpinions, batchDelSsOpinions,saveSsOpinionsById } from '@/api/ssOpinions'
    import EditForm from './ssOpinionsForm'
    import Etable from './ssOpinionsTable'
    

    const columns = [
        // {
        //     title: '主键id',
        //     sorter: true,
        //     width: '80px',
        //     dataIndex: 'id'
        // },   
         {
            title: '意见发起人',
            sorter: true,
            width: '150px',
            dataIndex: 'username'
        }, 
        {
            title: '意见发起电话',
            sorter: true,
            width: '150px',
            dataIndex: 'phone'
        },      
        {
            title: '意见内容',
            sorter: true,
            dataIndex: 'content'
        },        
        // {
        //     title: '意见回复父id',
        //     sorter: true,
        //     dataIndex: 'parentId'
        // }, 
        
        // {
        //     title: '商品id',
        //     sorter: true,
        //     dataIndex: 'goodsId'
        // },        
        // {
        //     title: '用户id',
        //     sorter: true,
        //     dataIndex: 'customerId'
        // },        
        // {
        //     title: '1-普通意见回复，2-商品评价',
        //     sorter: true,
        //     dataIndex: 'opinionType'
        // },        
        {
            title: '意见反馈时间',
            sorter: true,
            width: '150px',
            customRender: (text) => text ? moment(text).format('YYYY-MM-DD HH:mm') : '',
            dataIndex: 'createTime'
        },
        {
            title: '意见回馈内容',
            sorter: true,
            width: '150px',
            dataIndex: 'replyContent'
        },
        {
            title: '意见回馈时间',
            sorter: true,
            width: '150px',
            customRender: (text) => text ? moment(text).format('YYYY-MM-DD HH:mm') : '',
            dataIndex: 'replyTime'
        },
        {
            title: '回复状态',
            sorter: true,
            dataIndex: 'status',
             customRender:function (text) {
                if(text==1){
                    return '未回复'
                }else if(text==2){
                    return '已回复'
                }else{
                    return text
                }
            }
        },       
        {
            title: '操作',
            dataIndex: 'action',
            width: '150px',
            scopedSlots: { customRender: 'action' }
        }
    ]



    export default {
        name: 'TableList',
        components: {
            STable,
            Ellipsis,
            EditForm,
            Etable
        },
        data () {
            this.columns = columns
            return {
                // create model
                visible: false,
                tableVisible:false,
                title: '新增',
                confirmLoading: false,
                mdl: null,
                // 高级搜索 展开/关闭
                advanced: false,
                // 查询参数
                queryParam: {},
                // 加载数据方法 必须为 Promise 对象
                loadData: parameter => {
                    const requestParameters = Object.assign({}, parameter, this.queryParam)
                    // 删除为空字符串属性
                    Object.keys(requestParameters).forEach(item => {
                        if (!requestParameters[item]) delete requestParameters[item]
                    })
                    // 设置获取全部状态
                    if (requestParameters['status'] && requestParameters['status'] === 0) delete requestParameters['status']
                    console.log('loadData request parameters:', requestParameters)
                    return getSsOpinionsList(requestParameters)
                        .then(res => {
                            return res.result
                        })
                },
                selectedRowKeys: [],
                selectedRows: []
            }
        },
        filters: {
            statusFilter (type) {
                return statusMap[type].text
            },
            statusTypeFilter (type) {
                return statusMap[type].status
            }
        },
        computed: {
            rowSelection () {
                return {
                    selectedRowKeys: this.selectedRowKeys,
                    onChange: this.onSelectChange
                }
            }
        },
        methods: {
            handleAdd () {
                this.mdl = null
                this.title = '新增'
                this.visible = true
            },
            handleEdit (record) {
                if(record.status=="2"){
                     return this.$message.info('已回复')
                }
                this.title = '意见回复'
                this.visible = true
                this.mdl = { ...record }
            },
             handleTable (record) {
                this.title = '查看回复';
                this.mdl = { ...record.id };
                this.tableVisible = true;
            },
            handleOk () {
                const form = this.$refs.editForm.form
                this.confirmLoading = true
                form.validateFields((errors, values) => {
                    if (!errors) {
                         // 日期格式化
                            values.createTime = moment(values.createTime).format('YYYY-MM-DD HH:mm:ss')


                        if (values.id > 0) {
                            // 修改 e.g.

                            saveSsOpinionsById(values).then(res => {
                                this.visible = false
                                this.confirmLoading = false
                                // 重置表单数据
                                form.resetFields()
                                // 刷新表格
                                this.$refs.table.refresh()

                                this.$message.info('修改成功')
                            })
                        } else {
                            // 新增
                            saveSsOpinions(values).then(res => {
                                this.visible = false
                                this.confirmLoading = false
                                // 重置表单数据
                                form.resetFields()
                                // 刷新表格
                                this.$refs.table.refresh()

                                this.$message.info('新增成功')
                            })
                        }
                    } else {
                        this.confirmLoading = false
                    }
                })
            },
            handleconfirmDel () {
                this.$confirm({
                    title: '是否确认要删除选中数据?',
                    onOk: this.handleBatchDel,
                    class: '提示'
                })
            },
            handleBatchDel () {
                batchDelSsOpinions(this.selectedRowKeys).then(res => {
                    this.confirmLoading = false
                    // 刷新表格
                    this.$refs.table.refresh()

                    this.$message.info('删除成功')
                })
            },
            handleDel (record) {
                if (record.id > 0) {
                    // 修改 e.g.
                    delSsOpinions(record).then(res => {
                        this.confirmLoading = false
                        // 刷新表格
                        this.$refs.table.refresh()

                        this.$message.info('删除成功')
                    })
                }
            },

            handleCancel () {
                this.visible = false

                const form = this.$refs.editForm.form
                form.resetFields() // 清理表单数据（可不做）
            },

             handleCancel1 () {
                this.tableVisible = false

                const table = this.$refs.Etable.table
                table.resetFields() // 清理表单数据（可不做）
            },
            
            handleOk1 () {
                this.tableVisible = false

                const table = this.$refs.Etable.table
                table.resetFields() // 清理表单数据（可不做）
            },

            onSelectChange (selectedRowKeys, selectedRows) {
                this.selectedRowKeys = selectedRowKeys
                this.selectedRows = selectedRows
            },
            toggleAdvanced () {
                this.advanced = !this.advanced
            },
            resetSearchForm () {
                this.queryParam = {
                    date: moment(new Date())
                }
                // 刷新表格
                this.$refs.table.refresh()
            }
        }
    }
</script>
