<template>
  <div>
    <MemberGuruView v-if="isGuru" />
    <MemberUserView v-else />
  </div>
</template>

<script>
import MemberUserView from "@/views/member/MemberUserView.vue";
import MemberGuruView from "@/views/member/MemberGuruView.vue";

export default {
  components: {
    MemberGuruView,
    MemberUserView,
  },

  data() {
    return {
      isGuru: false,
      role: '',
      memberNickname: '',
    };
  },

  async mounted() {
    const urlParams = new URLSearchParams(window.location.search);
    this.memberNickname = urlParams.get('author');

    await this.fnMember(this.memberNickname);
  },

  methods: {
    async fnMember(memberNickname) {
      try {
        const res = await this.$axios.post(`/api/v1/member/${memberNickname}`);
        console.log("---ROLE: " + res.data.data.role);

        this.role = res.data.data.role;
        this.isGuru = this.role === "ROLE_GURU";
      } catch (err) {
        console.log(err);
      }
    },
  }
};
</script>
