<template>
  <a-layout>
    <a-layout-content
        :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }"
    >
      <p>
        <a-button type="primary" @click="add()" size="large">
          add
        </a-button>
      </p>
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
            <a-button type="primary" @click="edit(record)">
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
    <a-form :model="ebook" :label-col="{ span: 6 }" :wrapper-col="{ span: 18 }">
      <a-form-item label="cover">
        <a-input v-model:value="ebook.cover" />
      </a-form-item>
      <a-form-item label=name>
        <a-input v-model:value="ebook.name" />
      </a-form-item>
      <a-form-item label="Category 1">
        <a-input v-model:value="ebook.category1Id" />
      </a-form-item>
      <a-form-item label="Category 2">
        <a-input v-model:value="ebook.category2Id" />
      </a-form-item>
      <a-form-item label="Description">
        <a-input v-model:value="ebook.desc" type="textarea" />
      </a-form-item>
    </a-form>
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
    const ebook = ref({});
    const modalVisible = ref(false);
    const modalLoading = ref(false);
    const handleModalOk = () => {
      modalLoading.value = true;
      axios.post("/ebook/save", ebook.value).then((response) => {
        const data = response.data; // data = commonResp
        if (data.success) {
          modalVisible.value = false;
          modalLoading.value = false;

          // Reload
          handleQuery({
            page: pagination.value.current,
            size: pagination.value.pageSize,
          });
        }
      });
    };

    /**
     * Edit
     */
    const edit = (record: any) => {
      modalVisible.value = true;
      ebook.value = record
    };

    /**
     * add
     */
    const add = () => {
      modalVisible.value = true;
      ebook.value = {};
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
      add,

      ebook,
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
