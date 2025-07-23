const indexMixin = {
  data() {
    return {
      // 遮罩层
      loading: true,
      editOpen: false,
      editId: null,
      infoOpen: false,
      uploadOpen: false,
      // 导出遮罩层
      exportLoading: false,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        orderBy: null,
        orderByColumn: null,
        isAsc: null,
      },
    }
  },
  methods: {
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
      this.handleQueryAfter()
    },
    handleQueryAfter(){

    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    handleSortChange(e) {
      // console.log(e)
      this.queryParams.orderByColumn =  e.column.sortable
      this.queryParams.isAsc = e.order == 'ascending' ? 'asc' :'desc'
      this.getList()
    },
    //点击行触发，选中或不选中复选框
    handleRowClick(row, column, event){
      this.$refs.table.toggleRowSelection(row);
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.editId = null
      this.editOpen = true
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      const id = row.id || this.ids[0]
      this.editId = id
      this.editOpen = true
    },
    handleInfo(row) {
      const id = row.id || this.ids[0]
      this.editId = id
      this.infoOpen = true
    },
    handleImport() {
      this.uploadOpen = true
    },
  }
}

export default indexMixin
