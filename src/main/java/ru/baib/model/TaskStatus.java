package ru.baib.model;

public enum TaskStatus {

        RENDERING(1), COMPLETE(2);

        private int id;

        TaskStatus(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public static TaskStatus getById(int id) {
            for (TaskStatus status : values()) {
                if (status.id == id) {
                    return status;
                }
            }
            return null;
        }
}