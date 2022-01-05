<template>
  <v-container>
    <v-row>
      <v-col>
        <v-card outlined>
          <v-card-title>
            <span class="text-h5 font-weight-black">Bugs</span>

            <v-spacer></v-spacer>

            <v-btn
              class="mr-4"
              color="primary"
              dark
              @click="createBug"
            >
              <v-icon left>mdi-plus</v-icon>
              New Bug
            </v-btn>

            <v-btn
              color="primary"
              dark
              @click="getBugs"
            >
              <v-icon left>mdi-refresh</v-icon>
              Refresh
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
              <template v-slot:[`item.actions`]="{ item }">
                <v-tooltip bottom>
                  <template v-slot:activator="{ on, attrs }">
                    <v-icon
                      v-if="item.status=='Opened'"
                      class="mr-4"
                      color="green"
                      @click="resolveBug(item)"
                      v-bind="attrs"
                      v-on="on"
                    >
                      mdi-bug-check
                    </v-icon>
                  </template>
                  <span>Resolve</span>
                </v-tooltip>
                <v-tooltip bottom>
                  <template v-slot:activator="{ on, attrs }">
                    <v-icon
                      class="mr-4"
                      color="black"
                      @click="editBug(item)"
                      v-bind="attrs"
                      v-on="on"
                    >
                      mdi-pencil
                    </v-icon>
                  </template>
                  <span>Edit</span>
                </v-tooltip>
                <v-tooltip bottom>
                  <template v-slot:activator="{ on, attrs }">
                    <v-icon
                      color="red"
                      @click="deleteBug(item)"
                      v-bind="attrs"
                      v-on="on"
                    >
                      mdi-delete
                    </v-icon>
                  </template>
                  <span>Delete</span>
                </v-tooltip>
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

    <v-dialog v-model="newBugDialogShow" max-width="500px" persistent>
      <v-card>
        <v-card-title>
          Create New Bug
        </v-card-title>
        <bug-form
          ref="newBugForm"
          class="mx-4 mt-4"
          :summary.sync="newBugData.summary"
          :description.sync="newBugData.description"
          :valid.sync="newBugDataValid"
        >
        </bug-form>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn
            color="red"
            text
            @click="cancelCreateBug"
          >
            Cancel
          </v-btn>
          <v-btn
            color="green"
            text
            @click="confirmCreateBug"
            :disabled="!newBugDataValid"
          >
            Confirm
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-container>
</template>

<script>
import { mapState } from 'vuex'
import BugForm from '@/components/BugForm'

export default {
  name: 'BugManagement',
  components: {
    BugForm
  },
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
    bugs: [],
    newBugDialogShow: false,
    newBugData: {
      summary: '',
      description: '',
    },
    newBugDataValid: false
  }),
  methods: {
    setIsLoading(isLoading) {
      this.$store.dispatch('setIsLoading', isLoading)
    },
    getBugs() {
      this.setIsLoading(true)
      return this.$axios.get(`http://${this.apiIP}:${this.apiPort}/api/bug`).then((res) => {
        if (res.data.status == 200) return res.data.result
        else alert(`[${res.data.status}] ${res.data.error}`)
      })
      .then(bugs => {
        this.bugs = bugs
        this.setIsLoading(false)
      })
      .catch(err => {
        console.error(err)
        this.setIsLoading(false)
      })
    },
    getBugStatusColor(bug) {
      if (bug.status == 'Opened') {
        return 'green'
      } else {
        return 'gray'
      }
    },
    createBug() {
      this.newBugData.summary = 'New Bug'
      this.newBugData.description = ''
      this.$refs.newBugForm?.resetValidation()
      this.newBugDialogShow = true
    },
    confirmCreateBug() {
      console.log('create bug', this.newBugData.summary, this.newBugData.description)
      this.newBugDialogShow = false
    },
    cancelCreateBug() {
      this.newBugDialogShow = false
    },
    editBug(bug) {
      console.log('edit bug', bug)
    },
    deleteBug(bug) {
      console.log('delete bug', bug)
    },
    resolveBug(bug) {
      console.log('resolve bug', bug)
    }
  },
  mounted() {
    this.getBugs()
  },
}
</script>
