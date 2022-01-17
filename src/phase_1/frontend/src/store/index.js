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
  getters: {
    staffTypes() {
      return ['QA', 'RD']
    },
    staffToken(state) {
      switch (state.staffType) {
        case 'QA':
          return '7605d3247343467fb0b2b555dd1739081cdf72871ec84cae9a55fcab30fd0af8'
        case 'RD':
          return 'b6f7bec9ef844130b39d3c18d248829870d3f9fb78ca4465906ba1cb836176b6'
      }
      return ''
    }
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