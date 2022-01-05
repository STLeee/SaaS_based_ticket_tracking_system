<template>
  <v-container>
    <v-row>
      <v-col>
        <v-card outlined>
          <v-card-title>
            <span class="text-h5 font-weight-black">Bugs</span>

            <v-spacer></v-spacer>

            <v-btn
              icon
              large
              color="black"
              @click="getBugs"
              :loading="loading"
            >
              <v-icon>mdi-refresh</v-icon>
            </v-btn>
          </v-card-title>

          <v-card outlined class="mx-4">
            <v-data-table
              :items-per-page="10"
              :expanded.sync="bugExpanded"
              show-expand
              sort-by="id"
              :headers="bugHeaders"
              :items="bugs"
            >
              <template v-slot:[`item.status`]="{ item }">
                <v-chip
                  class="text-uppercase"
                  :color="getBugStatusColor(item)"
                  dark
                >
                  {{ item.status }}
                </v-chip>
              </template>
              <template v-slot:expanded-item="{ headers, item }">
                <td :colspan="headers.length">
                  {{ item.description }}
                </td>
              </template>
            </v-data-table>
          </v-card>
        </v-card>
      </v-col>
    </v-row>
    <v-row v-for="(bug, i) in bugs" :key="i">
    </v-row>
  </v-container>
</template>

<script>
import { mapState } from 'vuex'
export default {
  name: 'BugManagement',
  computed: {
    ...mapState([
      'stuffType',
      'apiIP',
      'apiPort'
    ])
  },
  data: () => ({
    loading: false,
    bugExpanded: [],
    bugHeaders: [
      {
        text: 'Status',
        value: 'status'
      },
      {
        text: 'ID',
        value: 'id',
        align: 'start'
      },
      {
        text: 'Summary',
        value: 'summary',
        align: 'start',
        sortable: false
      },
      {
        text: '',
        value: 'actions',
        align: 'end',
        sortable: false
      },
      {
        text: '',
        value: 'data-table-expand'
      },
    ],
    bugs: []
  }),
  methods: {
    getBugs() {
      this.loading = true
      return this.$axios.get(`http://${this.apiIP}:${this.apiPort}/api/bug`).then((res) => {
        console.log('res', res)
        if (res.data.status == 200) return res.data.result
        else alert(`[${res.data.status}] ${res.data.error}`)
      })
      .then(bugs => {
        this.bugs = bugs
        this.loading = false
      })
      .catch(err => {
        console.error(err)
        this.loading = false
      })
    },
    getBugStatusColor(bug) {
      if (bug.status == 'Opened') {
        return 'green'
      } else {
        return 'gray'
      }
    }
  },
  mounted() {
    this.getBugs()
  },
}
</script>
