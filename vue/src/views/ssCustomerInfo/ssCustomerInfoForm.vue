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
        <a-form-item label="客户ID">
          <a-input v-decorator="['customerId', {rules: [{required: true, min: 1, message: '请输入至少五个字符的规则描述！'}]}]" />
        </a-form-item>
        <a-form-item label="客户名称">
          <a-input v-decorator="['customerName', {rules: [{required: true, min: 1, message: '请输入至少五个字符的规则描述！'}]}]" />
        </a-form-item>
        <a-form-item label="所属水站">
          <a-select show-search option-filter-prop="children" :filter-option="filterOption" v-decorator="['stationId', { rules: [{ required: true, message: '请选择水站!' }] }]" >
            <a-select-option v-for="s in stationList" :key="s.id" :value="s.id">
              {{ s.station_name }}
            </a-select-option>
          </a-select>
        </a-form-item>
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
        'customerId',
        'customerName',
        'stationId'
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
                stationList:[]
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

            this.getWaterStationData()
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
          },
          filterOption(input, option) {
            return (
              option.componentOptions.children[0].text.toLowerCase().indexOf(input.toLowerCase()) >= 0
            );
          }
        }
    }
</script>
