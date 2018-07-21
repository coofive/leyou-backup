<template>
  <v-card>
    <v-card-title>
      <!--TODO 增加品牌-->
      <v-flex xs3>
        <v-btn color="primary" @click="addBrand">新增品牌</v-btn>
      </v-flex>
      <v-spacer/>
      <v-flex xs3>
        <v-text-field label="输入关键字搜索" v-model.lazy="search" hide-details/>
      </v-flex>
    </v-card-title>
    <v-divider/>
    <v-data-table
      :headers="headers"
      :items="brands"
      :pagination.sync="pagination"
      :total-items="totalBrands"
      :loading="loading"
      class="elevation-1"
    >
      <template slot="items" slot-scope="props">
        <td class="text-xs-center">{{ props.item.id }}</td>
        <td class="text-xs-center">{{ props.item.name }}</td>
        <td class="text-xs-center">
          <img v-if="props.item.image" :src="props.item.image" width="130" height="40"/>
          <span v-else>无</span>
        </td>
        <td class="text-xs-center">{{ props.item.letter }}</td>
        <td class="justify-center layout px-0">
          <v-btn icon @click="editBrand(props.item)">
            <i class="el-icon-edit"/>
          </v-btn>
          <v-btn icon @click="deleteBrand(props.item)">
            <i class="el-icon-delete"/>
          </v-btn>
        </td>
      </template>
    </v-data-table>

    <!--弹出对话框-->
    <v-dialog max-width="500" v-model="show" persistent scrollable>
      <v-card>
        <!--对话框标题-->
        <v-toolbar dense dark color="primary">
          <v-toolbar-title>新增品牌</v-toolbar-title>
          <v-spacer/>
          <!--关闭对话框-->
          <v-btn icon @click="closeWindow">
            <v-icon>close</v-icon>
          </v-btn>
        </v-toolbar>
        <!--对话框内容，表单-->
        <v-card-text class="px-5" style="height:400px">
          <new-brand-form @close="closeWindow" :oldBrand="oldBrand" :isEdit="isEdit"/>
        </v-card-text>
      </v-card>
    </v-dialog>
  </v-card>

</template>

<script>
  // 导入自定义的表单组件
  import NewBrandForm from './NewBrandForm'

  export default {
    name: "new-brand",
    data() {
      return {
        headers: [
          {text: 'id', value: 'id', align: 'center', sortable: true},
          {text: '名称', value: 'name', align: 'center', sortable: false},
          {text: 'LOGO', value: 'image', align: 'center', sortable: false},
          {text: '首字母', value: 'letter', align: 'center', sortable: true},
          {text: '操作', value: 'letter', align: 'center', sortable: false}
        ],
        brands: [],
        pagination: {},
        totalBrands: 0,
        loading: true,
        search: '', // 搜索过滤字段
        isEdit: false,  // 是否是编辑，用来判断发出的请求是put或post
        show: false,  // 控制对话框的显示
        oldBrand: {},  // 即将被编辑的品牌数据
      }
    },
    mounted() { // 渲染后执行
      this.getDataFromServer();
    },
    watch: {
      pagination: {
        deep: true, // 监视pagination属性变化
        handler() {
          console.log("NewBrand：watch...");
          this.getDataFromServer();
        }
      },
      search: {
        handler() {
          console.log("NewBrand:search...");
          this.getDataFromServer();
        }
      }
    },
    methods: {
      getDataFromServer() {
        // 从后端加载数据
        this.$http.get("/item/brand/page", {
          params: {
            key: this.search,
            page: this.pagination.page,
            rows: this.pagination.rowsPerPage,
            sortBy: this.pagination.sortBy,
            desc: this.pagination.descending
          }
        }).then(resp => {
          this.brands = resp.data.items;
          this.totalBrands = resp.data.total;

          // 完成赋值后，设置加载状态
          this.loading = false;
        });
      },
      addBrand() {
        // 修改标记
        this.isEdit = false;
        // 打开弹窗
        this.show = true;
        // 把oldBrand设置为null
        this.oldBrand = null;
      },
      closeWindow() {
        // 加载数据
        this.getDataFromServer();
        // 关闭窗口
        this.show = false;
      }
    },
    components: {
       NewBrandForm
    }
  }
</script>

<style scoped>

</style>
