import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

const store = new Vuex.Store({
  state: {
    isLoading: false,
    staffType: 'QA',
    apiIP: '127.0.0.1',
    apiPort: '80',
  },
  mutations: {
    setIsLoading (state, isLoading) {
      state.isLoading = isLoading
    },
    setStaffType (state, type) {
      state.staffType = type
    },
    setAPIIP (state, ip) {
      state.apiIP = ip
    },
    setAPIPort (state, port) {
      state.apiPort = port
    },
  },
  actions: {
    setIsLoading (context, isLoading) {
      context.commit('setIsLoading', isLoading)
    },
    setStaffType (context, type) {
      context.commit('setStaffType', type)
    },
    setAPIIP (context, ip) {
      context.commit('setAPIIP', ip)
    },
    setAPIPort (context, port) {
      context.commit('setAPIPort', port)
    },
  }
})

export default store;