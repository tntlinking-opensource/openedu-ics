import {getToken} from "@/utils/auth";

export const editData = {

  methods: {
    getFormPromise(form) {
      return new Promise(resolve => {
        form.validate(res => {
          resolve(res);
        });
      });
    },
  }
}
