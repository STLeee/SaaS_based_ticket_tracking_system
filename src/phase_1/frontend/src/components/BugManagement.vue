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
              @click="toCreateBug"
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
              sort-desc
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
                      @click="toResolveBug(item)"
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
                      @click="toEditBug(item)"
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
                      @click="toDeleteBug(item)"
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
          :summary.sync="bugFormData.summary"
          :description.sync="bugFormData.description"
          :valid.sync="bugFormDataValid"
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
            :disabled="!bugFormDataValid"
          >
            Confirm
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <v-dialog v-model="editBugDialogShow" max-width="500px" persistent>
      <v-card>
        <v-card-title>
          Edit Bug {{ beEditedBug? `(${beEditedBug.id})`: '' }}
        </v-card-title>
        <bug-form
          ref="editBugForm"
          class="mx-4 mt-4"
          :summary.sync="bugFormData.summary"
          :description.sync="bugFormData.description"
          :valid.sync="bugFormDataValid"
        >
        </bug-form>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn
            color="red"
            text
            @click="cancelEditBug"
          >
            Cancel
          </v-btn>
          <v-btn
            color="green"
            text
            @click="confirmEditBug"
            :disabled="!bugFormDataValid"
          >
            Confirm
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <v-dialog v-model="deleteBugDialogShow" max-width="500px" persistent>
      <v-card>
        <v-card-title>
          Delete Bug {{ beEditedBug? `(${beEditedBug.id})`: '' }}
        </v-card-title>
        <v-card-text>
          Are you sure to delete this bug?
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn
            color="red"
            text
            @click="cancelDeleteBug"
          >
            Cancel
          </v-btn>
          <v-btn
            color="green"
            text
            @click="confirmDeleteBug"
          >
            Confirm
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <v-dialog v-model="resolveBugDialogShow" max-width="500px" persistent>
      <v-card>
        <v-card-title>
          Resolve Bug {{ beEditedBug? `(${beEditedBug.id})`: '' }}
        </v-card-title>
        <v-card-text>
          Are you sure to resolve this bug?
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn
            color="red"
            text
            @click="cancelResolveBug"
          >
            Cancel
          </v-btn>
          <v-btn
            color="green"
            text
            @click="confirmResolveBug"
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
    editBugDialogShow: false,
    deleteBugDialogShow: false,
    resolveBugDialogShow: false,
    bugFormData: {
      summary: '',
      description: '',
    },
    bugFormDataValid: false,
    beEditedBug: null
  }),
  methods: {
    setIsLoading(isLoading) {
      this.$store.dispatch('setIsLoading', isLoading)
    },
    getAPIConfig() {
      return {
        baseURL: `http://${this.apiIP}:${this.apiPort}`,
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${this.stuffType}`
        },
      }
    },
    getBugs() {
      this.setIsLoading(true)
      return this.$axios.get('/api/bug', this.getAPIConfig()).then((res) => {
        if (res.data.status == 200) return res.data.result
        else return Promise.reject(`[${res.data.status}] ${res.data.error}`)
      })
      .then(result => {
        this.bugs = result
        this.setIsLoading(false)
      })
      .catch(err => {
        alert(err)
        console.error(err)
        this.setIsLoading(false)
      })
    },
    createBug(summary, description) {
      this.setIsLoading(true)
      return this.$axios.post('/api/bug', { summary, description }, this.getAPIConfig()).then((res) => {
        if (res.data.status == 200) return res.data.result
        else return Promise.reject(`[${res.data.status}] ${res.data.error}`)
      })
      .then(result => {
        this.bugs.push(result)
        this.setIsLoading(false)
      })
      .catch(err => {
        alert(err)
        console.error(err)
        this.setIsLoading(false)
      })
    },
    editBug(bug, summary, description) {
      console.log('edit bug', bug, summary, description)
    },
    deleteBug(bug) {
      console.log('delete bug', bug)
    },
    resolveBug(bug) {
      console.log('resolve bug', bug)
    },
    getBugStatusColor(bug) {
      if (bug.status == 'Opened') {
        return 'green'
      } else {
        return 'gray'
      }
    },
    toCreateBug() {
      this.bugFormData.summary = 'New Bug'
      this.bugFormData.description = ''
      this.$refs.newBugForm?.resetValidation()
      this.newBugDialogShow = true
    },
    confirmCreateBug() {
      this.createBug(this.bugFormData.summary, this.bugFormData.description)
      this.newBugDialogShow = false
    },
    cancelCreateBug() {
      this.newBugDialogShow = false
    },
    toEditBug(bug) {
      this.beEditedBug = bug
      this.bugFormData.summary = bug.summary
      this.bugFormData.description = bug.description
      this.$refs.editBugForm?.resetValidation()
      this.editBugDialogShow = true
    },
    confirmEditBug() {
      this.editBug(this.beEditedBug, this.bugFormData.summary, this.bugFormData.description)
      this.editBugDialogShow = false
    },
    cancelEditBug() {
      this.editBugDialogShow = false
    },
    toDeleteBug(bug) {
      this.beEditedBug = bug
      this.deleteBugDialogShow = true
    },
    confirmDeleteBug() {
      this.deleteBug(this.beEditedBug)
      this.deleteBugDialogShow = false
    },
    cancelDeleteBug() {
      this.deleteBugDialogShow = false
    },
    toResolveBug(bug) {
      this.beEditedBug = bug
      this.resolveBugDialogShow = true
    },
    confirmResolveBug() {
      this.resolveBug(this.beEditedBug)
      this.resolveBugDialogShow = false
    },
    cancelResolveBug() {
      this.resolveBugDialogShow = false
    }
  },
  mounted() {
    this.getBugs()
  },
}
</script>
