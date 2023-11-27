<template>
  <div class="d-flex justify-content-end">
    추가 할  기술 명 :&nbsp; &nbsp; <input type="text" id="input-2" required @change="onChangeInput">&nbsp; &nbsp;
    <button type="button" class="btn btn-warning btn-rounded" v-on:click="createSkill">추가</button>&nbsp;
    <button type="button" class="btn btn-primary btn-rounded" v-on:click="SkillUpdate">수정</button>&nbsp;
  </div>

  <table class="table table-striped table-bordered text-center">
    <thead>
    <tr>
      <th>스킬 아이디</th>
      <th>스킬 이름</th>
    </tr>
    </thead>

    <tbody>
    <tr v-for="(item, idx) in list" :key="idx" >
      <td>{{ item.skillId }}</td>
      <td>{{ item.name }}</td>
    </tr>
    </tbody>
  </table>
</template>

<script>
export default {
  data() {
    return {
      skillRequestDto: {
        name: ''
      },
      list: [],
      InputName:''
    };
  },
  mounted() {
    this.SkillList();
  },
  methods: {
    SkillList() {
      this.$axios
          .get("/api/v1/admin/skill/all")
          .then((res) => {
            this.list = res.data.data;
          })
          .catch((err) => {
            alert(err.response.data.message)
          });
    },
    createSkill() {
      this.skillRequestDto = {
        name: this.InputName
      }
      this.$axios
          .post("/api/v1/admin/skill", this.skillRequestDto)
          .then(() => {
            location.reload()
          })
          .catch((err) => {
            if (err.response.status === 401 || err.response.status === 404) {
              this.$router.push({ path: '/login' });
            } else {
              alert(err.response.data.message);
            }
          });
    },
    SkillUpdate() {
      this.$router.push({
        path: '/admin/skill',
      })
    },
    onChangeInput(event) {
      this.InputName = event.target.value
    },
  },
};
</script>
