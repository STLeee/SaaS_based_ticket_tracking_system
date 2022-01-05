<template>
  <v-app>
    <v-app-bar app color="primary" dark>
      <div class="d-flex align-center">
        <span class="mr-2 text-h5 font-weight-black text-uppercase">Stuff</span>
        <v-select
          id="stuff-type-select"
          class="text-h5 font-weight-black"
          outlined
          hide-details
          :items="stuffTypes"
          :value="stuffType"
          @input="setStuffType"
        ></v-select>
      </div>

      <v-spacer></v-spacer>

      <div class="d-flex align-center">
        <span class="mr-2 text-h5 font-weight-black text-uppercase">API IP</span>
        <v-text-field
          id="api-ip-input"
          class="text-h5 font-weight-black"
          hide-details
          outlined
          :value="apiIP"
          @input="setAPIIP"
        ></v-text-field>
        <span class="mx-2 text-h5 font-weight-black text-uppercase">:</span>
        <v-text-field
          id="api-port-input"
          class="text-h5 font-weight-black"
          hide-details
          outlined
          :value="apiPort"
          @input="setAPIPort"
        ></v-text-field>
      </div>
    </v-app-bar>

    <v-main>
      <BugManagement/>
    </v-main>
  </v-app>
</template>

<script>
import { mapState } from 'vuex'
import BugManagement from './components/BugManagement';

export default {
  name: 'App',
  components: {
    BugManagement,
  },
  computed: {
    ...mapState([
      'stuffType',
      'apiIP',
      'apiPort'
    ])
  },
  data: () => ({
    stuffTypes: ['QA', 'RD'],
  }),
  methods: {
    setStuffType(type) {
      this.$store.dispatch('setStuffType', type)
    },
    setAPIIP (ip) {
      console.log(ip)
      this.$store.dispatch('setAPIIP', ip)
    },
    setAPIPort (port) {
      this.$store.dispatch('setAPIPort', port)
    },
  }
};
</script>

<style lang="scss">
#stuff-type-select {
  max-width: 10px;
}
#api-ip-input {
  max-width: 200px;
}
#api-port-input {
  max-width: 60px;
}
</style>