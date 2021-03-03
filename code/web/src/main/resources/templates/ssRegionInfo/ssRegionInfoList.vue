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
                                    <a-select-option :value="100">正常</a-select-option>
                                    <a-select-option :value="101">禁用</a-select-option>
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
            <a v-action:edit @click="handleEdit(record)">修改</a>
            <a-divider type="vertical" />
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
        </a-card>
    </page-header-wrapper>
</template>

<script>
    import moment from 'moment'
    import { STable, Ellipsis } from '@/components'
    import { statusMap } from '@/api/RC'
    import { getSsRegionInfoList, saveSsRegionInfo, delSsRegionInfo, batchDelSsRegionInfo } from '@/api/ssRegionInfo'
    import EditForm from './ssRegionInfoForm'

    const columns = [
        {
            title: '编号',
            sorter: true,
            dataIndex: 'areaId'
        },        {
            title: '区域编码',
            sorter: true,
            dataIndex: 'areacode'
        },        {
            title: '区域名称',
            sorter: true,
            dataIndex: 'areadesc'
        },        {
            title: '区域级别：1地市；2区县；3网格； 4乡镇 ；5村',
            sorter: true,
            dataIndex: 'areatype'
        },        {
            title: '父节点',
            sorter: true,
            dataIndex: 'parentarea'
        },        {
            title: '状态：0禁用；1启用',
            sorter: true,
            width: '100px',
            scopedSlots: { customRender: 'status' },
            dataIndex: 'status'
        },        {
            title: '短名称',
            sorter: true,
            dataIndex: 'shortName'
        },        {
            title: '',
            sorter: true,
            dataIndex: 'regionCode'
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
            EditForm
        },
        data () {
            this.columns = columns
            return {
                // create model
                visible: false,
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
                    return getSsRegionInfoList(requestParameters)
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
                this.title = '修改'
                this.visible = true
                this.mdl = { ...record }
            },
            handleOk () {
                const form = this.$refs.editForm.form
                this.confirmLoading = true
                form.validateFields((errors, values) => {
                    if (!errors) {
                         // 日期格式化


                        if (values.id > 0) {
                            // 修改 e.g.

                            saveSsRegionInfo(values).then(res => {
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
                            saveSsRegionInfo(values).then(res => {
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
                batchDelSsRegionInfo(this.selectedRowKeys).then(res => {
                    this.confirmLoading = false
                    // 刷新表格
                    this.$refs.table.refresh()

                    this.$message.info('删除成功')
                })
            },
            handleDel (record) {
                if (record.id > 0) {
                    // 修改 e.g.
                    delSsRegionInfo(record).then(res => {
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
