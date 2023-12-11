<template>
  <div class="black-bg" v-if="modalOpen === true">
    <div class="white-bg">
      <b-button @click="modalOpen = false" class="modal-exit-btn">닫기</b-button>
      <h1>{{ updateMemberId }}번 회원 상세</h1>

      <div class="info-row">
        <div class="info">
          <b-form>
            <div class="form-group row mb-3">
              <label for="input-1" class="col-md-4 col-form-label text-md-right">이메일</label>
              <div class="col-md-8">
                <b-form-input readonly v-model="MemberRequestDto.email" type="email" id="input-1"
                              required>{{ MemberRequestDto.email }}
                </b-form-input>
              </div>
            </div>

            <div class="form-group row mb-3">
              <label for="input-3" class="col-md-4 col-form-label text-md-right">비밀번호</label>
              <div class="col-md-8">
                <b-form-input readonly v-model="MemberRequestDto.password" type="text" id="input-3"
                              required>{{ MemberRequestDto.password }}
                </b-form-input>
              </div>
            </div>

            <div class="form-group row mb-3">
              <label for="input-2" class="col-md-4 col-form-label text-md-right">이름</label>
              <div class="col-md-8">
                <b-form-input v-model="MemberRequestDto.name" type="text" id="input-2"
                              required>{{ MemberRequestDto.name }}
                </b-form-input>
              </div>
            </div>

            <div class="form-group row mb-3">
              <label for="input-4" class="col-md-4 col-form-label text-md-right">닉네임</label>
              <div class="col-md-8">
                <b-form-input v-model="MemberRequestDto.nickname" type="text" id="input-4"
                              required>{{ MemberRequestDto.nickname }}
                </b-form-input>
              </div>
            </div>

            <div class="form-group row mb-3">
              <label for="input-5" class="col-md-4 col-form-label text-md-right">전화번호</label>
              <div class="col-md-8">
                <b-form-input v-model="MemberRequestDto.telephone" type="tel" id="input-5"
                              required>{{ MemberRequestDto.telephone }}
                </b-form-input>
              </div>
            </div>

            <div class="form-group row mb-3">
              <label for="input-5" class="col-md-4 col-form-label text-md-right">직업</label>
              <div class="col-md-8">
                <b-form-input v-model="MemberRequestDto.job" type="tel" id="input-5"
                              required>{{ MemberRequestDto.job }}
                </b-form-input>
              </div>
            </div>

            <div class="form-group row mb-3">
              <label for="input-6" class="col-md-4 col-form-label text-md-right">지번</label>
              <div class="col-md-8">
                <b-form-input v-model="MemberRequestDto.zoneCode" type="text" id="input-6"
                              required>{{ MemberRequestDto.zoneCode }}
                </b-form-input>
              </div>
            </div>

            <div class="form-group row mb-3">
              <label for="input-6" class="col-md-4 col-form-label text-md-right">도로명주소</label>
              <div class="col-md-8">
                <b-form-input v-model="MemberRequestDto.roadAddress" type="text" id="input-6"
                              required>{{ MemberRequestDto.roadAddress }}
                </b-form-input>
              </div>
            </div>

            <div class="form-group row mb-3">
              <label for="input-6" class="col-md-4 col-form-label text-md-right">상세주소</label>
              <div class="col-md-8">
                <b-form-input v-model="MemberRequestDto.detailAddress" type="text" id="input-6"
                              required>{{ MemberRequestDto.detailAddress }}
                </b-form-input>
              </div>
            </div>

            <div class="form-group row mb-3">
              <label for="input-7" class="col-md-4 col-form-label text-md-right">생년 월일 :</label>
              <div class="col-md-8">
                <b-form-input v-model="MemberRequestDto.birthDate" type="date" id="input-7"
                              required></b-form-input>
              </div>
            </div>

            <div class="form-group row mb-4">
              <label for="input-8" class="col-md-4 col-form-label text-md-right">현재 보유 기술:</label>
              <div class="col-md-8">
                {{ MemberRequestDto.skillName }}
              </div>
            </div>

            <div class="form-group row mb-4">
              <label for="input-8" class="col-md-4 col-form-label text-md-right">성별:</label>
              <div class="col-md-8 d-flex align-items-center">
                <div class="mr-3">
                  <b-form-radio v-model="MemberRequestDto.gender" name="userGender" value="MALE">남성</b-form-radio>
                </div>
                <div>
                  <b-form-radio v-model="MemberRequestDto.gender" name="userGender" value="FEMALE">여성
                  </b-form-radio>
                </div>
              </div>
            </div>

            <div class="form-group row mb-4">
              <label for="input-6" class="col-md-4 col-form-label text-md-right">권한:</label>
              <div class="col-md-8">
                <select v-model="MemberRequestDto.role">
                  <option disabled value="">권한을 선택해 주세요</option>
                  <option>ROLE_USER</option>
                  <option>ROLE_MANAGER</option>
                  <option>ROLE_ADMIN</option>
                </select>
              </div>
            </div>

            <div class="form-group row mb-4">
              <label for="input-7" class="col-md-4 col-form-label text-md-right">생성일:</label>
              <div class="col-md-8">
                {{ MemberRequestDto.regDate }}
              </div>
            </div>

            <div class="form-group row mb-4">
              <label for="input-7" class="col-md-4 col-form-label text-md-right">수정일:</label>
              <div class="col-md-8">
                {{ MemberRequestDto.modDate }}
              </div>
            </div>

            <div class="form-group row mb-4">
              <label for="input-7" class="col-md-4 col-form-label text-md-right">삭제여부:</label>
              <div class="col-md-8">
                {{ MemberRequestDto.isDelete }}
              </div>
            </div>

            <hr>

            <div class="text-center">
              <b-button @click="[modalOpen = false,updateMember()]"
                        class="modal-exit-btn button-button">
                변경
              </b-button>
            </div>
          </b-form>
        </div>
      </div>
    </div>
  </div>


  <b-button v-on:click="toReportPostList">게시글 신고 목록</b-button>
  <b-button v-on:click="toReportReplyList">댓글 신고 목록</b-button>
  <b-button v-on:click="toSkillList">스킬 목록</b-button>
  <b-button v-on:click="toPayList">결제 목록</b-button>

  <div class="board-list mt-5">
    <table class="table table-striped">
      <colgroup>
        <col style="width: 5%;"/> <!-- No 열의 너비 -->
        <col style="width: auto;"/> <!-- 카테고리 열의 너비 -->
        <col style="width: 15%;"/> <!-- 제목 열의 너비를 최대한 확보하고 나머지 열은 자동 조정 -->
        <col style="width: 20%;"/> <!-- 게시글 열의 너비 -->
        <col style="width: 20%;"/> <!-- 댓글 열의 너비 -->
        <col style="width: 20%;"/> <!-- 댓글 열의 너비 -->
      </colgroup>
      <thead>
      <tr>
        <th scope="col">MemberId</th>
        <th scope="col">Nickname</th>
        <th scope="col">Role</th>
        <th scope="col">Post</th>
        <th scope="col">Reply</th>
        <th scope="col">Review</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="(item, idx) in list" :key="idx" class="hover-pointer">
        <td>{{ item.memberId }}</td>
        <td @click="modalOpen = true">
          <b-button type="button" class="w3-button w3-round w3-blue-gray" v-on:click="clickModel(item)">
            {{ item.nickname }}
          </b-button>
        </td>
        <td>{{ item.role }}</td>
        <td>
          <b-button v-on:click="toMemberPost(item.memberId)">게시물 보기</b-button>
        </td>
        <td>
          <b-button v-on:click="toMemberReply(item.memberId)">댓글 보기</b-button>
        </td>
        <td>
          <b-button v-on:click="toMemberReview(item.memberId)">후기 보기</b-button>
        </td>
      </tr>
      </tbody>
    </table>
  </div>
</template>


<script>
export default {
  data() { //변수생성
    return {
      requestBody: {},
      modalOpen: false,
      updateMemberId: '',
      MemberRequestDto: {
        password: '',
        name: '',
        nickname: '',
        telephone: '',
        // address: '',
        zoneCode: '',
        roadAddress: '',
        detailAddress: '',
        birthDate: '',
        gender: '',
        role: '',
        skillName: [],
        email: '',
        regDate: '',
        modDate: '',
        isDelete: '',
        job: '',
      },
      list: {}, //리스트 데이터

    }
  },
  mounted() {
    this.fnMemberList()
  },
  methods: {
    fnMemberList() {
      this.$axios.get("/api/v1/admin/member/all").then((res) => {
        console.log(res.data.data);
        this.list = res.data.data  //서버에서 데이터를 목록으로 보내므로 바로 할당하여 사용할 수 있다.
      }).catch((err) => {
        if (err.response.status === 401 || err.response.status === 404) {
          this.$router.push({path: '/login'});
        } else {
          alert(err.response.data.message);
          location.reload()
        }
      })
    },
    clickModel(item) {
      this.MemberRequestDto.email = item.email
      this.MemberRequestDto.role = item.role
      this.MemberRequestDto.password = item.password
      this.MemberRequestDto.name = item.name
      this.MemberRequestDto.nickname = item.nickname
      // this.MemberRequestDto.address = item.address
      this.MemberRequestDto.zoneCode = item.zoneCode
      this.MemberRequestDto.roadAddress = item.roadAddress
      this.MemberRequestDto.detailAddress = item.detailAddress
      this.MemberRequestDto.birthDate = item.birthDate
      this.MemberRequestDto.gender = item.gender
      this.MemberRequestDto.skillName = item.skillName
      this.MemberRequestDto.telephone = item.telephone
      this.MemberRequestDto.regDate = item.regDate
      this.MemberRequestDto.modDate = item.modDate
      this.MemberRequestDto.isDelete = item.isDelete
      this.MemberRequestDto.job = item.job
      this.updateMemberId = item.memberId
    },
    updateMember() {
      this.$axios.post("/api/v1/admin/member/" + this.updateMemberId, this.MemberRequestDto)
          .then(() => {
            alert('멤버 수정이 성공적으로 완료되었어요! ')
            this.SendList()
          }).catch((err) => {
        if (err.response.status === 401 || err.response.status === 404) {
          this.$router.push({path: '/login'});
        } else {
          alert(err.response.data.message);
          location.reload()
        }
      })
    },
    SendList() {
      this.$router.push({
        path: '/admin/main',
      })
      location.reload()
    },
    toMemberPost(memberId) {
      this.requestBody.idx = memberId
      this.$router.push({
        path: '/admin/member/post',
        query: this.requestBody
      })
    },
    toMemberReply(memberId) {
      this.requestBody.idx = memberId
      this.$router.push({
        path: '/admin/member/reply',
        query: this.requestBody
      })
    },
    toMemberReview(memberId) {
      this.requestBody.idx = memberId
      this.$router.push({
        path: '/admin/member/review',
        query: this.requestBody
      })
    },
    toReportPostList() {
      this.$router.push({
        path: '/admin/reportPost/list',
      })
    },
    toReportReplyList() {
      this.$router.push({
        path: '/admin/reportReply/list',
      })
    },
    toSkillList() {
      this.$router.push({
        path: '/admin/skill/list',
      })
    },
    toPayList() {
      this.$router.push({
        path: '/admin/pay/list',
      })
    },
  }
}
</script>

<style scoped>
.black-bg {
  display: flex;
  align-items: center;
  justify-content: center; /* 수평 가운데 정렬 */
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.432);
  position: fixed; /* 고정된 위치 */
  top: 0;
  left: 0;
}

.white-bg {
  width: 70%;
  max-width: 600px; /* 최대 너비 설정 */
  max-height: 80vh; /* 최대 높이 설정 (화면 높이의 80%까지만 사용) */
  overflow-y: auto; /* 내용이 모달을 벗어날 경우 스크롤 표시 */
  background-color: white;
  border: 2px solid black;
  border-radius: 8px;
  padding: 20px;
  text-align: left;
}

.modal-exit-btn {
  float: right;
  margin-top: 0;
  margin-right: 0;
}

.modal-exit-btn:hover {
  cursor: pointer;
}

.button-container {
  text-align: right;
}

.button-button {
  margin-top: 20px;
}

.text-center {
  text-align: center;
}
</style>
