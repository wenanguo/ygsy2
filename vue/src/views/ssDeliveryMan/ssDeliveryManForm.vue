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
        <a-form-item label="配送员姓名">
          <a-input v-decorator="['username', {rules: [{required: true, min: 1, message: '请输入至少五个字符的规则描述！'}]}]" />
        </a-form-item>
        <a-form-item label="手机号" v-show="model==null || model.id == 0">
          <a-input v-decorator="['phone', {rules: [{required: true, message: '请输入正确的手机号！'}]}]" />
        </a-form-item>
        <a-form-item label="配送员类型">
          <a-select v-decorator="['ttype', {rules: [{required: true, message: '请选择！'}]}]">
            <a-select-option :value="3">水站配送员</a-select-option>
            <a-select-option :value="4">水厂配送员</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="所属水站">
          <a-select v-decorator="['stationId', { rules: [{ required: true, message: '请选择水站!' }] }]" >
            <a-select-option v-for="s in stationList" :key="s.id" :value="s.id">
              {{ s.station_name }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <!--
        <a-form-item label="微信号">
          <a-input v-decorator="['wxId', {rules: [{required: true, min: 1, message: '请输入至少五个字符的规则描述！'}]}]" />
        </a-form-item>
        -->
        <a-form-item label="性别">
          <a-radio-group v-decorator="['sex', { initialValue: 1 }]">
            <a-radio :value="1">男</a-radio>
            <a-radio :value="2">女</a-radio>
          </a-radio-group>
        </a-form-item>
        <a-form-item label="状态">
          <a-radio-group v-decorator="['status', { initialValue: 100 }]">
            <a-radio :value="100">正常</a-radio>
            <a-radio :value="101">禁用</a-radio>
          </a-radio-group>
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
    import pick from 'lodash.pick'
    import { getWaterStationDict } from '@/api/ssWaterStation'

    // 表单字段
    const fields = [
        'id',
        'username',
        'phone',
        'sex',
        'ttype',
        'stationId',
        'status'
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
                form: this.$form.createForm(this),
                stationList: []
            }
        },
        created () {
            console.log('custom modal created')

            // 防止表单未注册
            fields.forEach(v => this.form.getFieldDecorator(v))

            this.getWaterStationData()

            // 当 model 发生改变时，为表单设置值
            this.$watch('model', () => {
                this.model && this.form.setFieldsValue(pick(this.model, fields))
            })

            this.stationList = []
        },
        methods: {
          getWaterStationData (params) {
              // 获取水站字典
              getWaterStationDict().then(res => {
                  if (res.code === 200) {
                      this.stationList = res.result
                  }
              })
          }
        }
    }
</script>
