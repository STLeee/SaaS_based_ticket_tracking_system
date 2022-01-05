import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

const store = new Vuex.Store({
  state: {
    stuffType: 'QA',
    apiIP: '127.0.0.1',
    apiPort: '3000',
  },
  mutations: {
    setStuffType (state, type) {
      state.stuffType = type
    },
    setAPIIP (state, ip) {
      state.apiIP = ip
    },
    setAPIPort (state, port) {
      state.apiPort = port
    },
  },
  actions: {
    setStuffType (context, type) {
      context.commit('setStuffType', type)
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