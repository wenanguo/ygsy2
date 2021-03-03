<template>
  <a-modal
    :title="title"
    :width="1040"
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
        <a-form-item label="水站名称">
          <a-input v-decorator="['stationName', {rules: [{required: true, min: 1, message: '请输入至少五个字符的规则描述！'}]}]" />
        </a-form-item>
        <a-form-item label="负责人姓名">
          <a-input v-decorator="['leaderName', {rules: [{required: true, min: 1, message: '请输入至少五个字符的规则描述！'}]}]" />
        </a-form-item>
        <a-form-item label="手机号">
          <a-input v-decorator="['phone', {rules: [{required: true, min: 1, message: '请输入至少五个字符的规则描述！'}]}]" />
        </a-form-item>
        <a-form-item label="座机">
          <a-input v-decorator="['landline', {rules: [{required: false, min: 1, message: '请输入至少五个字符的规则描述！'}]}]" />
        </a-form-item>
        <a-form-item label="状态">
          <a-radio-group v-decorator="['status', { initialValue: 100 }]">
            <a-radio :value="100">正常</a-radio>
            <a-radio :value="101">禁用</a-radio>
          </a-radio-group>
        </a-form-item>
        <!--
        <a-form-item label="所在地市">
          <a-select v-decorator="['city', { rules: [{ required: true, message: '请选择地市!' }] }]" @change="handleCityChange">
            <a-select-option v-for="r in cityList" :key="r.areacode" :value="r.areacode">
              {{ r.areadesc }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="所在区县">
          <a-select v-decorator="['county', { rules: [{ required: true, message: '请选择区县!' }] }]" @change="handleCountyChange">
            <a-select-option v-for="r in countyList" :key="r.areacode" :value="r.areacode">
              {{ r.areadesc }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="所在乡镇">
          <a-select v-decorator="['town', { rules: [{ required: true, message: '请选择乡镇!' }] }]" >
            <a-select-option v-for="r in townList" :key="r.areacode" :value="r.areacode">
              {{ r.areadesc }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="水站详细地址">
          <a-input v-decorator="['address', {rules: [{required: true, min: 1, message: '请输入至少五个字符的规则描述！'}]}]" />
        </a-form-item>
        <a-form-item label="水站位置">
          经度：<a-input-number :min="1" style="margin-right:10px" v-decorator="['latitude', {rules: [{required: true, message: '请输入经度！'}]}]" />
          维度：<a-input-number :min="1" v-decorator="['longitude', {rules: [{required: true, message: '请输入维度！'}]}]" />
        </a-form-item>
        -->
        <a-form-item label="地图选点">
          <baidu-map :style="{width:map.width,height:map.height}" class="map" ak="HIztk0ifyOeqlRxuO5KGc5tm" :zoom="map.zoom" :center="{lng: map.center.lng, lat: map.center.lat}" :scroll-wheel-zoom="true" @click="getLocationPoint">
          </baidu-map>
        </a-form-item>         
        <a-form-item label="水站详细地址">
          <a-input v-decorator="['address', {rules: [{required: true, min: 1, message: '请输入至少五个字符的规则描述！'}]}]"/>
        </a-form-item>    
        <a-form-item label="经度">
          <a-input v-decorator="['longitude', {rules: [{required: true, message: '请输入经度！'}]}]" disabled/>
        </a-form-item>    
        <a-form-item label="维度">
          <a-input v-decorator="['latitude', {rules: [{required: true, message: '请输入维度！'}]}]" disabled/>
        </a-form-item>   

        <a-form-item label="可用余额">
          <a-input-number :min="1" v-decorator="['availableBalance', {rules: [{required: false, message: '请输入经度！'}]}]" />
        </a-form-item>
        <a-form-item label="冻结余额">
          <a-input-number :min="1" v-decorator="['frozenBalance', {rules: [{required: false, message: '请输入经度！'}]}]" />
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
    import pick from 'lodash.pick'
    import { getRegionDict } from '@/api/ssWaterStation'
    import BaiduMap from 'vue-baidu-map/components/map/Map.vue'

    // 表单字段
    const fields = [
        'id',
        'stationName',
        'leaderName',
        'phone',
        'landline',
        'province',
        'city',
        'county',
        'town',
        'address',
        'latitude',
        'longitude',
        'status',
        'availableBalance',
        'frozenBalance'
    ]

    export default {
        components:{
          BaiduMap
        },
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
                /**provinceList: [],
                cityList: [],
                countyList: [],
                townList: [],**/
                map:{
                    center: {lng: 104.912843,lat:25.093919},//'黔西南州人民政府',
                    zoom: 14,
                    width:'700px',
                    height:'510px'
                }
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

            /**
            this.provinceList = []
            this.cityList = []
            this.countyList = []
            this.townList = []
            this.getReionInfoList('GUIZ', 1) //页面初始化时查询贵州省下地市
            **/
        },
        methods: {
          /*
          getReionInfoList (value, level) {
              var params = {
                parentarea: value
              }
              // 获取区域字典
              getRegionDict(params).then(res => {
                  if (res.code === 200) {
                      if(level === 1){
                        this.cityList = res.result
                      }else if(level === 2){
                        this.countyList = res.result
                      }else if(level === 3){
                        this.townList = res.result
                      }else{
                        this.provinceList = res.result
                      }
                  }
              })
          },
          handleProvinceChange (v) {  //选择省份后联动地市
               this.getReionInfoList(v, 1);
          },
          handleCityChange (v) {     //选择地市后联动区县
              this.getReionInfoList(v, 2);
          },
          handleCountyChange (v) {   //选择区县后联动乡镇
             this.getReionInfoList(v, 3);
          },*/
          getLocationPoint(e) {
            var lng = e.point.lng;
            var lat = e.point.lat;
            this.form.setFieldsValue({longitude: lng})
            this.form.setFieldsValue({latitude: lat})
            //console.log(e.point.lng, e.point.lat);

            new BMap.Geocoder().getLocation(e.point, rs => { // 当前位置定位
              const province = rs.addressComponents.province // 省
              const city = rs.addressComponents.city // 城市
              const district = rs.addressComponents.district // 区县
              const street = rs.addressComponents.street // 街道
              const streetNumber = rs.addressComponents.streetNumber// 门牌号
              const detail_address = province + city + district + street + streetNumber // 组装成地址            
              const address = rs.address
              const biz = rs.business  //商圈
              // const poi = rs.surroundingPois
              
              this.form.setFieldsValue({address: detail_address})
            })
          }
        }
    }
</script>
