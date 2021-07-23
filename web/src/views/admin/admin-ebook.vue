<template>
  <a-layout>
    <a-layout-content
        :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }"
    >
      <a-table
          :columns="columns"
          :row-key="record => record.id"
          :data-source="ebooks"
          :pagination="pagination"
          :loading="loading"
          @change="handleTableChange"
      >
        <template #cover="{ text: cover }">
          <img v-if="cover" :src="cover" alt="avatar" />
        </template>
        <template v-slot:action="{ text, record }">
          <a-space size="small">
            <a-button type="primary" @click="edit">
              Edit
            </a-button>
            <a-button type="danger">
              Delete
            </a-button>
          </a-space>
        </template>
      </a-table>
    </a-layout-content>
  </a-layout>
  <a-modal
      title="Ebook List"
      v-model:visible="modalVisible"
      :confirm-loading="modalLoading"
      @ok="handleModalOk"
  >
    <p>test</p>
  </a-modal>
</template>

<script lang="ts">
import { defineComponent, onMounted, ref } from 'vue';
import axios from 'axios';

export default defineComponent({
  name: 'AdminEbook',
  setup() {
    const ebooks = ref();
    const pagination = ref({
      current: 1,
      pageSize: 4,
      total: 0
    });
    const loading = ref(false);

    const columns = [
      {
        title: 'Cover',
        dataIndex: 'cover',
        slots: { customRender: 'cover' }
      },
      {
        title: 'Name',
        dataIndex: 'name'
      },
      {
        title: 'Category 1',
        key: 'category1Id',
        dataIndex: 'category1Id'
      },
      {
        title: 'Category 2',
        dataIndex: 'category2Id'
      },
      {
        title: 'Docs Count',
        dataIndex: 'docCount'
      },
      {
        title: 'Views Count',
        dataIndex: 'viewCount'
      },
      {
        title: 'Upvotes Count',
        dataIndex: 'voteCount'
      },
      {
        title: 'Action',
        key: 'action',
        slots: { customRender: 'action' }
      }
    ];

    /**
     * data query
     **/
    const handleQuery = (params: any) => {
      loading.value = true;
      axios.get("/ebook/list", {
        params: {
          page: params.page,
          size: params.size
        }
      }).then((response) => {
        loading.value = false;
        const data = response.data;
        ebooks.value = data.content.list;

        // 重置分页按钮
        pagination.value.current = params.page;
        pagination.value.total = data.content.total;
      });
    };

    /**
     * Triggered when clicked
     */
    const handleTableChange = (pagination: any) => {
      console.log("params for pagination：" + pagination);
      handleQuery({
        page: pagination.current,
        size: pagination.pageSize
      });
    };
    // -------- List ---------
    const modalVisible = ref(false);
    const modalLoading = ref(false);
    const handleModalOk = () => {
      modalLoading.value = true;
      setTimeout(() => {
        modalVisible.value = false;
        modalLoading.value = false;
      }, 2000);
    };

    /**
     * Edit
     */
    const edit = () => {
      modalVisible.value = true;
    };

    onMounted(() => {
      handleQuery({
        page : 1,
        size : pagination.value.pageSize
      });
    });

    return {
      ebooks,
      pagination,
      columns,
      loading,
      handleTableChange,

      edit,

      modalVisible,
      modalLoading,
      handleModalOk
    }
  }
});
</script>

<style scoped>
img {
  width: 50px;
  height: 50px;
}
</style>
