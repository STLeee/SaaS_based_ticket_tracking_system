<template>
  <v-container>
    <v-row class="text-center">
      <v-col>
        <v-row>
        </v-row>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
  export default {
    name: 'BugManagement',
    data: () => ({
      apiServer: '127.0.0.1',
      apiPort: 3000,
    }),

    methods: {
      getBugs() {
        return this.$axios.get(`http://${this.apiServer}:${this.apiPort}/api/bug`).then((res) => {
          if (res.data.status == 200) return res.data.result
          else return new Promise.reject(res.data)
        })
      }
    },
    mounted() {
      this.getBugs()
      .then(bugs => {
        console.log('bugs', bugs)
      })
      .catch(err => {
        if (err.status) {
          alert(`[${err.status}] ${err.error}`)
        }
      })
    },
  }
</script>
