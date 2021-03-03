<template>
    <a-modal
            :title="title"
            :width="640"
            :visible="visible"
            :confirmLoading="loading"
            @ok="() => { $emit('ok') }"
            @cancel="() => { $emit('cancel') }"
    >
        <a-spin :spinning="loading">
            <a-form :form="form" v-bind="formLayout">
                                        <!-- 检查是否有 id 并且大于0，大于0是修改。其他是新增，新增不显示主键ID -->
                    <a-form-item v-show="model && model.id > 0" label="编号">
                        <a-input v-decorator="['id', { initialValue: 0 }]" disabled />
                    </a-form-item>
                    <a-form-item label="水站编号">
                        <a-input v-decorator="['stationId', {rules: [{required: true, min: 1, message: '请输入至少五个字符的规则描述！'}]}]" />
                    </a-form-item>
                    <a-form-item label="订单编号">
                        <a-input v-decorator="['orderNumber', {rules: [{required: true, min: 1, message: '请输入至少五个字符的规则描述！'}]}]" />
                    </a-form-item>
                    <a-form-item label="订单状态 1-待分配，2-配送中，3-已完成，4-已取消">
                        <a-input v-decorator="['orderStatus', {rules: [{required: true, min: 1, message: '请输入至少五个字符的规则描述！'}]}]" />
                    </a-form-item>
                    <a-form-item label="客户ID">
                        <a-input v-decorator="['customerId', {rules: [{required: true, min: 1, message: '请输入至少五个字符的规则描述！'}]}]" />
                    </a-form-item>
                    <a-form-item label="订单总金额">
                        <a-input v-decorator="['totalPrice', {rules: [{required: true, min: 1, message: '请输入至少五个字符的规则描述！'}]}]" />
                    </a-form-item>
                    <a-form-item label="描述">
                        <a-input v-decorator="['description', {rules: [{required: true, min: 1, message: '请输入至少五个字符的规则描述！'}]}]" />
                    </a-form-item>
                    <a-form-item label="下单时间">
                        <a-date-picker style="width: 100%" show-time v-decorator="['orderDate', {rules: [{required: true}]}]" >
                        </a-date-picker>
                    </a-form-item>
                    <a-form-item label="预约时间">
                        <a-date-picker style="width: 100%" show-time v-decorator="['appointTime', {rules: [{required: true}]}]" >
                        </a-date-picker>
                    </a-form-item>
            </a-form>
        </a-spin>
    </a-modal>
</template>

<script>
    import pick from 'lodash.pick'

    // 表单字段
    const fields = [
        'id',
        'stationId',
        'orderNumber',
        'orderStatus',
        'customerId',
        'totalPrice',
        'description',
        'orderDate',
        'appointTime'
    ]

    export default {
        props: {
            visible: {
                type: Boolean,
                required: true
            },
            title: {
                type: String,
                required: true
            },
            loading: {
                type: Boolean,
                default: () => false
            },
            model: {
                type: Object,
                default: () => null
            }
        },
        data () {
            this.formLayout = {
                labelCol: {
                    xs: { span: 24 },
                    sm: { span: 7 }
                },
                wrapperCol: {
                    xs: { span: 24 },
                    sm: { span: 13 }
                }
            }
            return {
                form: this.$form.createForm(this)
            }
        },
        created () {
            console.log('custom modal created')

            // 防止表单未注册
            fields.forEach(v => this.form.getFieldDecorator(v))

            // 当 model 发生改变时，为表单设置值
            this.$watch('model', () => {
                this.model && this.form.setFieldsValue(pick(this.model, fields))
            })
        }
    }
</script>
