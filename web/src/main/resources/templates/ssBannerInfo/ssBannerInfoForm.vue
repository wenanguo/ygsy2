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
                    <a-form-item label="banner名称">
                        <a-input v-decorator="['bannerName', {rules: [{required: true, min: 1, message: '请输入至少五个字符的规则描述！'}]}]" />
                    </a-form-item>
                    <a-form-item label="banner图片">
                        <a-input v-decorator="['bannerImg', {rules: [{required: true, min: 1, message: '请输入至少五个字符的规则描述！'}]}]" />
                    </a-form-item>
                    <a-form-item label="banner内容">
                        <a-input v-decorator="['bannerContent', {rules: [{required: true, min: 1, message: '请输入至少五个字符的规则描述！'}]}]" />
                    </a-form-item>
                    <a-form-item label="商品编码">
                        <a-input v-decorator="['goodsCode', {rules: [{required: true, min: 1, message: '请输入至少五个字符的规则描述！'}]}]" />
                    </a-form-item>
                    <a-form-item label="排序号">
                        <a-input v-decorator="['sort', {rules: [{required: true, min: 1, message: '请输入至少五个字符的规则描述！'}]}]" />
                    </a-form-item>
                        <a-form-item label="状态">
                            <a-radio-group v-decorator="['status', { initialValue: 100 }]">
                                <a-radio :value="100">正常</a-radio>
                                <a-radio :value="101">禁用</a-radio>
                            </a-radio-group>
                        </a-form-item>
                    <a-form-item label="失效时间">
                        <a-date-picker style="width: 100%" show-time v-decorator="['expiredTime', {rules: [{required: true}]}]" >
                        </a-date-picker>
                    </a-form-item>
                    <a-form-item label="创建人">
                        <a-input v-decorator="['createBy', {rules: [{required: true, min: 1, message: '请输入至少五个字符的规则描述！'}]}]" />
                    </a-form-item>
                    <a-form-item label="创建时间">
                        <a-date-picker style="width: 100%" show-time v-decorator="['createTime', {rules: [{required: true}]}]" >
                        </a-date-picker>
                    </a-form-item>
                    <a-form-item label="最后一次更新人">
                        <a-input v-decorator="['lastUpdateBy', {rules: [{required: true, min: 1, message: '请输入至少五个字符的规则描述！'}]}]" />
                    </a-form-item>
                    <a-form-item label="最后一次更新时间">
                        <a-date-picker style="width: 100%" show-time v-decorator="['lastUpdateTime', {rules: [{required: true}]}]" >
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
        'bannerName',
        'bannerImg',
        'bannerContent',
        'goodsCode',
        'sort',
        'status',
        'expiredTime',
        'createBy',
        'createTime',
        'lastUpdateBy',
        'lastUpdateTime'
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
