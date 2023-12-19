import { createStore } from 'vuex'

export default createStore({
  state: {
    user: {
      id: 0,
      userAccount: 'lcj',
      userPassword: 18,
      secretKey: 'a36dd8c00a54939a7bb906f3fc5be8a3bb6b68edbfe2de56419699d722422d4b',
      accessKey: '78f824d1817e6be7e3eadc018a73885e71a48d3b82ad938d97c6486b410cf37d'
    }
  },
  getters: {
  },
  mutations: {
    updateUser (state, Account) {
      this.state.user.userAccount = Account.userAccount
      this.state.user.userPassword = Account.userPassword
      this.state.user.secretKey = Account.secretKey
      this.state.user.accessKey = Account.accessKey
      this.state.user.id = Account.id
    }
  },
  actions: {
  },
  modules: {
  }
})
