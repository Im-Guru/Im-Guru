<template>
  <div class="board-list">
    <div class="common-buttons">
      <button type="button" class="w3-button w3-round w3-blue-gray" v-on:click="fnWrite">등록</button>
    </div>
    <table class="w3-table-all">
      <thead>
      <tr>
        <th>No</th>
        <th>제목</th>
        <th>작성자</th>
        <th>조회수</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="(item, idx) in state.items" :key="idx">
        <td>{{ item.postId }}</td>
        <td>{{ item.title }}</td>
        <td>{{ item.memberNickname }}</td>
        <td>{{ item.viewCnt }}</td>
      </tr>
      </tbody>
    </table>
  </div>
</template>
<script>
import {reactive} from "vue";
import axios from "axios";
export default {
  setup() {
    const state = reactive({
      items: [],
      form: {
        name: "",

      }
    })
    const load = () => {
      axios.get("/api/v1/post/all").then(({data}) => {
        console.log(data);
        state.items = data.data;
      })
    };
    load();
    return {state}
  }
}
</script>

<style scoped>
</style>