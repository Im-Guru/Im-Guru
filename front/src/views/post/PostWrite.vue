<template>
  <div class="post-write mt-5">

    <h4 class="text-center mb-4"><strong>게시글 작성</strong></h4>

    <div class="row mb-3">
      <div class="col-2 d-flex flex-column align-items-center">
        <label for="postCategory" class="mt-3"><strong>카테고리</strong></label>
      </div>
      <div class="col-sm">
        <b-form-select v-model="postCategory" :options="userOptions" v-if="this.role === 'ROLE_USER'" id="postCategory"></b-form-select>
        <b-form-select v-model="postCategory" :options="guruOptions" v-if="this.role === 'ROLE_GURU'" id="postCategory"></b-form-select>
        <b-form-select v-model="postCategory" :options="adminOptions" v-if="this.role === 'ROLE_ADMIN'" id="postCategory"></b-form-select>
      </div>
    </div>

    <div class="row mb-3" v-if="this.role === 'ROLE_GURU'">
      <div class="col-2 d-flex flex-column align-items-center">
        <label for="postCategory" class="mt-3"><strong>전문기술</strong></label>
      </div>
      <div class="col">
        <b-form-input type="text" v-model="skillName" readonly></b-form-input>
      </div>
    </div>

    <div class="row mb-3" v-if="this.role === 'ROLE_GURU' && this.postCategory === 'FINDUSER'">
      <div class="col-2 d-flex flex-column align-items-center">
        <label for="postTitle" class="mt-3"><strong>가격</strong></label>
      </div>
      <div class="col">
        <b-form-input type="text" v-model="price" placeholder="가격을 입력해주세요." ref="priceInput">
        </b-form-input>

        <!-- Display a warning if the input is not a valid number -->
        <div v-if="!/^[1-9]\d*$/.test(price) && this.price">
          <strong style="color: red;" class="mt-1">잘못된 가격입니다. 다시 입력해주세요.</strong>
        </div>

      </div>
    </div>

    <div class="row mb-3">
      <div class="col-2 d-flex flex-column align-items-center">
        <label for="postTitle" class="mt-3"><strong>제목</strong></label>
      </div>
      <div class="col">
        <b-form-input type="text" v-model="title" placeholder="제목을 입력해주세요." ref="titleInput">
        </b-form-input>
      </div>
    </div>


    <div class="mb-3">
      <ck-editor v-model="content" :editor="editor" :config="editorConfig" />
    </div>

    <input type="file" ref="fileInput" @change="handleFileChange" multiple/>

    <!-- 업로드된 파일 목록 표시 -->
    <div v-for="(file, index) in existsFiles" :key="index">
      {{ file.fileName }}
      <!--      {{ file.fileUrl }}-->
      <!-- 여기에 다른 파일 정보 표시를 추가할 수 있음 -->
    </div>

    <div class="d-flex justify-content-end">
      <button type="button" class="btn btn-primary btn-rounded" v-on:click="fnSave">저장</button>&nbsp;
      <button type="button" class="btn btn-success btn-rounded" v-on:click="fnList">목록</button>
    </div>
  </div>
</template>

<script>
import CKEditor from '@ckeditor/ckeditor5-vue';
import ClassicEditor from '@ckeditor/ckeditor5-build-classic';
// import UploadAdapter from './UploadAdapter';

export default {
  components: {'ck-editor': CKEditor.component},

  data() {
    return {
      createDto: {
        title: "",
        content: "",
        categoryName: "",
        price: "",
        files: [], // 파일 업로드 관련 필드 추가
      },
      editor: ClassicEditor,
      editorConfig: {
        height: '300px', // 높이 설정 추가
        toolbar: ['heading', '|', 'bold', 'italic', '|', 'bulletedList', 'numberedList', 'indent', 'outdent', '|',  'insertTable', 'link', '|', 'undo', 'redo'],
        table: {
          contentToolbar: ['tableColumn', 'tableRow', 'mergeTableCells', 'tableProperties', 'tableCellProperties'],
        },
        // extraPlugins: [function (editor) {
        //   editor.plugins.get('FileRepository').createUploadAdapter = (loader) => {
        //     return new UploadAdapter(loader);
        //   }
        // }],

      },
      requestBody: this.$route.query,
      idx: this.$route.query.idx,
      title: '',
      content: '',
      price:'',
      created_at: '',
      postCategory: 'FREE',
      skillName: null, // 선택된 기술을 저장할 변수
      userOptions: [
        {value: 'FREE', text: '자유게시판'},
        {value: 'QNA', text: '질문있어요'},
        {value: 'INFO', text: '정보나눠요'},
        {value: 'HELP', text: '도와주세요'},
        {value: 'FINDGURU', text: '도사찾아요'},
      ],
      guruOptions: [
        {value: 'FREE', text: '자유게시판'},
        {value: 'INFO', text: '정보나눠요'},
        {value: 'FINDUSER', text: '도와드려요'},
      ],
      adminOptions: [
        {value: 'NOTICE', text: '공지사항'},
        {value: 'FREE', text: '자유게시판'},
        {value: 'QNA', text: '질문글'},
        {value: 'INFO', text: '정보공유'},
      ],
      role: '',
      existsFiles: [],

      postId: '',
    }
  },
  created() {
    this.checkGuruSkill();
  },
  mounted() {
    this.fnGetView();
    this.fnLoginMember();
  },
  methods: {
    fnLoginMember() {
      this.$axios.post(`/api/v1/member/myInfo`, "", {
        headers: {
          Authorization: `Bearer ${localStorage.getItem('user_token')}`
        }
      }).then((res) => {
        console.log(res);
        this.memberNickname = res.data.data.nickname;
        this.role = res.data.data.role;
      }).catch((err) => {
        console.log(err);

        // if (err.response.status === 401 || err.response.status === 400) {
        //   alert("로그인을 먼저 해주세요!");
        //   this.$router.push({path: '/login'});
        // }
        // if (err.response.status === 404) {
        //   alert("잘못된 경로입니다.");
        //   alert(err.response.data.message);
        //   location.reload()
        // } else {
        //   alert(err.response.data.message);
        //   location.reload()
        // }
        // this.$store.state.loadingStatus = false;
      })
    },
    handleFileChange(event) {
      this.createDto.files = Array.from(event.target.files); // 선택한 모든 파일을 배열로 저장
    },
    async checkGuruSkill() {
      this.$axios.post('/api/v1/skill/checkGuruSkill', null, {
        headers: {
          Authorization: `Bearer ${localStorage.getItem("user_token")}`,
        },
      }).then((res) => {
        this.skillName = res.data.data.name;
      }).catch((err) => {
        if (err.response.status === 401 || err.response.status === 404) {
          this.$router.push({ path: '/login' });
        } else {
          alert(err.response.data.message);
        }
      })
    },

    fnGetView() {
      if (this.idx !== undefined) {
        this.$axios.get('/api/v1/post/' + this.idx, {
          params: this.requestBody
        }).then((res) => {
          console.log(res.data.data)
          this.title = res.data.data.title
          this.author = res.data.data.memberNickname
          this.content = res.data.data.content
          this.price = res.data.data.price
          this.skillName = res.data.data.skillName
          this.created_at = res.data.data.regDate
          this.existsFiles = res.data.data.fileList

          console.log('Exists Files:', this.existsFiles);
        }).catch((err) => {
          if (err.response.status === 401 || err.response.status === 404) {
            this.$router.push({ path: '/login' });
          } else {
            alert(err.response.data.message);
          }
        })
      }
    },
    fnList() {
      delete this.requestBody.idx
      this.$router.push({
        path: './list',
        query: this.requestBody
      })
    },
    fnView(idx) {
      this.requestBody.idx = idx
      this.$router.push({
        path: './detail',
        query: this.requestBody
      })
    },
    fnSave() {
      if (localStorage.getItem("user_token") === null) {
        alert("로그인 해야 가능한 서비스입니다.");
        return;
      }

      this.createDto.title = this.title
      this.createDto.content = this.content
      this.createDto.price = this.price || 0
      this.createDto.categoryName = this.postCategory

      if (!this.createDto.title) {
        alert("제목을 입력해주세요.");
        return;
      }
      if (!this.createDto.content) {
        alert("내용을 입력해주세요.");
        return;
      }

      if (!this.idx) {
        // 새 게시글 작성
        this.$axios.post("/api/v1/post", this.createDto, {
          headers: {
            Authorization: `Bearer ${localStorage.getItem("user_token")}`,
          },
        }).then((response) => {
          alert("게시글을 작성하였습니다.");
          this.postId = response.data.data;
          this.fnView(response.data.data);

          if (this.createDto.files.length !== 0) {
            for (const file of this.createDto.files) {
              const fileCategory = "post";
              const fileKey = this.postId;

              const formData = new FormData();
              const modifiedFilename = "post_" + this.postId + "_" + file.name;
              formData.append("file", file, modifiedFilename);

              // 파일 업로드
              this.$axios.post("/upload", formData, {
                withCredentials: true,  // CORS 관련 설정
              }).then((uploadResponse) => {
                console.log("--upload--");
                console.log(uploadResponse);
              }).catch((uploadError) => {
                console.log(uploadError);
              });

              // file DB에 저장
              this.$axios.post(`/api/v1/file/${fileCategory}/${fileKey}/${file.name}`).then((res) => {
                console.log("--File DB--");
                console.log(res);
              }).catch((err) => {
                console.log(err);
              });
            }

          }
        }).catch((err) => {
          if (err.response.status === 401 || err.response.status === 404) {
            this.$router.push({ path: '/login' });
          } else {
            alert(err.response.data.message);
            location.reload();
          }
        });
      } else {
        // 게시글 업데이트
        this.$axios.patch("/api/v1/post/" + this.idx, this.createDto, {
          headers: {
            Authorization: `Bearer ${localStorage.getItem("user_token")}`,
          },
        }).then((res) => {

          if (this.createDto.files.length !== 0) {
            for (const file of this.createDto.files) {
              const fileCategory = "post";
              const fileKey = this.idx;

              const formData = new FormData();
              const modifiedFilename = "post_" + this.idx + "_" + file.name;
              formData.append("file", file, modifiedFilename);

              // 파일 업로드
              this.$axios.post("/upload", formData, {
                withCredentials: true,  // CORS 관련 설정
              }).then((uploadResponse) => {
                console.log("--upload--");
                console.log(uploadResponse);
              }).catch((uploadError) => {
                console.log(uploadError);
              });

              // file DB에 저장
              this.$axios.post(`/api/v1/file/${fileCategory}/${fileKey}/${file.name}`).then((res) => {
                console.log("--File DB--");
                console.log(res);
              }).catch((err) => {
                console.log(err);
              });
            }

          }

          // alert(res.data.message);
          alert("게시글이 수정되었습니다.");
          console.log(res.data.data);
          this.fnView(res.data.data.postId);
        }).catch((err) => {
          if (err.response.status === 401 || err.response.status === 404) {
            this.$router.push({ path: '/login' });
          } else {
            alert(err.response.data.message);
            location.reload()
          }
        });

      }
    },
  }
}
</script>
<style scoped>

</style>