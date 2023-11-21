// src/service/loginAPI.js
import axios from "axios"

let logoutTimeout; // 로그아웃 타이머 변수

const getUserInfo = (userId, userPw) => {
    const reqData = {
        'email': userId,
        'password': userPw
    }

    return axios.post('api/v1/login', reqData, {
        headers: {
            'Content-type': 'application/json'
        }
    })
}

export default {
    async doLogin(userId, userPw) {
        try {
            const getUserInfoPromise = getUserInfo(userId, userPw)
            const [userInfoResponse] = await Promise.all([getUserInfoPromise])
            if (userInfoResponse.data.length === 0) {
                return 'notFound'
            } else {
                console.log(userInfoResponse)
                const accessToken = userInfoResponse.data.data.token.accessToken;
                const role = userInfoResponse.data.data.role;
                // const nickname = userInfoResponse.data.data.nickname;
                const expirationTime = new Date().getTime() + (1800000); //30분
                // const expirationTime = new Date().getTime() + (5000); //5초

                // 유효 시간과 사용자 정보를 로컬 스토리지에 저장
                localStorage.setItem('user_token', accessToken);
                localStorage.setItem('user_role', role);
                // localStorage.setItem('user_nickname', nickname);
                localStorage.setItem('user_expiration', expirationTime);

                // 유효 시간이 경과하면 자동으로 로그아웃되도록 타이머 설정
                const timeUntilExpiration = expirationTime - new Date().getTime();
                logoutTimeout = setTimeout(() => {
                    this.logout();
                }, timeUntilExpiration);

                return userInfoResponse;
            }
        } catch (err) {
            console.error(err);
        }
    },
    // 로그아웃 메서드 추가
    logout() {
        // 로그아웃 시 로컬 스토리지의 관련 정보 삭제
        localStorage.removeItem('user_token');
        localStorage.removeItem('user_role');
        // localStorage.removeItem('user_nickname');
        localStorage.removeItem('user_expiration');

        // 로그아웃 타이머 해제
        clearTimeout(logoutTimeout);
        alert("세션이 만료되어 로그아웃 합니다")
        location.reload()
    },
    // 로그인 상태 확인 메서드 추가
    isLoggedIn() {
        // 로컬 스토리지에서 유효 시간과 사용자 정보를 가져옴
        const expirationTime = localStorage.getItem('user_expiration');

        // 유효 시간이 경과하면 자동으로 로그아웃
        if (expirationTime && new Date().getTime() > parseInt(expirationTime, 10)) {
            this.logout();
            return false;
        }

        // 유효 시간이 남아있거나 없는 경우는 로그인 상태로 간주
        return true;
    }
}
