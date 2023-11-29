<template>

    <div class="m-black-bg" v-if="modalOpen === true">
        <div class="m-white-bg">
            <div class="m-button-container">
                <b-button @click="modalOpen = false" class="m-modal-exit-btn">닫기</b-button>
            </div>
            <h1>{{ modalList.title }}</h1>

            <div class="info-row">
                <div class="info">
                    <span class="m-sendName">받는사람: {{ modalList.receiverName }}</span>
                    <span class="create-at">&nbsp&nbsp 날짜: {{ formatDateTime(modalList.regDate) }}</span>
                </div>
            </div>
            <hr>
            <h2>{{ modalList.content }}</h2>
        </div>
    </div>

    <div class="board-list mt-5">
        <table class="w3-table-all">
            <thead>
            <tr>
                <th>No</th>
                <th>받는 사람</th>
                <th>제목</th>
                <th>보낸 사람</th>

            </tr>
            </thead>
            <tbody>
            <tr v-for="(item, idx) in list" :key="idx">
                <td>{{ idx }}</td>
                <td>{{ item.receiverName }}</td>
                <td @click="modalOpen = true">
                    <b-button v-if="item.title.length<20" v-on:click="clickModel(item)">{{ item.title }}</b-button>
                    <b-button v-else v-on:click="clickModel(item)">{{ item.title.substring(0, 20) + "..." }}</b-button>
                </td>
                <td>{{ item.senderName }}</td>
            </tr>
            </tbody>
        </table>
    </div>

</template>

<script>
export default {
    data() {
        return {
            list: {},
            modalList: {},
            modalOpen: false,
        }
    },
    mounted() {
        this.SendListGet()
    },
    methods: {
        formatDateTime(dateTime) {
            const date = new Date(dateTime);
            const year = date.getFullYear();
            const month = (date.getMonth() + 1).toString().padStart(2, '0');
            const day = date.getDate().toString().padStart(2, '0');
            const hours = date.getHours().toString().padStart(2, '0');
            const minutes = date.getMinutes().toString().padStart(2, '0');

            return `${year}-${month}-${day} ${hours}:${minutes}`;
        },

        SendListGet() {
            this.$axios.get("/api/v1/message/send", {
                headers: {
                    Authorization: `Bearer ${localStorage.getItem('user_token')}`
                }
            })
                .then((res) => {
                    this.list = res.data.data
                }).catch((err) => {
              if (err.response.status === 401 || err.response.status === 404) {
                this.$router.push({ path: '/login' });
              } else {
                alert(err.response.data.message);
              }
            })
        },
        clickModel(item) {
            this.modalList = item
        },

    }
}
</script>

<style>
.m-sendName {
  color: #ff2b1d;
}

.receiverName {
  color: #204bff;
}

.m-black-bg {
  display: flex;
  align-items: center;
  justify-content: center; /* 수평, 수직 가운데 정렬을 위해 추가 */
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.432);
  position: fixed;
  padding: 5px;
}

.m-white-bg {
  width: 60%;
  height: 50%;
  background-color: white;
  padding: 20px;
  border-radius: 8px;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%); /* 화면 중앙으로 이동 */
  text-align: left;
  border: 2px solid black; /* 테두리 추가 (검은색, 2px 두께) */
}

.m-button-container {
  text-align: right;
}

.m-modal-exit-btn {
  margin-top: 0;
  margin-right: 0;
  float: right;
}

.m-button-button {
  position: absolute;
  bottom: 20px;
  right: 20px;
}

.m-modal-exit-btn:hover {
  cursor: pointer;
}
</style>