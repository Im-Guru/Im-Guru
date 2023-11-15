// src/vuex/getters.js
export default {
    getUserId: state => state.userId,
    getErrorState: state => state.errorState,
    getIsAuth: state => state.isAuth,
    loggedIn(state) {
        return !!state.user
    }
}