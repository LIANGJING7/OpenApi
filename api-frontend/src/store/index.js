import { createStore } from 'vuex'

export default createStore({
  state: {
    user: {
      id: 0,
      userAccount: '',
      userPassword: '',
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
      this.state.user.id = Account.id
      this.state.user.secretKey = Account.secretKey
      this.state.user.accessKey = Account.accessKeyy
    },
    updateInterfaceInfo (state, Account) {
      this.state.interfaceInfoData.name = Account.name
      this.state.interfaceInfoData.status = Account.status
      this.state.interfaceInfoData.description = Account.description
      this.state.interfaceInfoData.url = Account.url
      this.state.interfaceInfoData.method = Account.method
      this.state.interfaceInfoData.params = Account.params
      this.state.interfaceInfoData.requestHeader = Account.requestHeader
      this.state.interfaceInfoData.responseHeader = Account.responseHeader
    }
  },
  actions: {
  },
  modules: {
  }
})
