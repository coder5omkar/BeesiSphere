import React, { useEffect } from "react";
import { useState } from "react";
import { getTodo, saveTodo, updateTodo } from "../services/TodoService";
import { useNavigate, useParams } from "react-router-dom";

const FrequencyEnum = {
  DAILY: "DAILY",
  WEEKLY: "WEEKLY",
  BIWEEKLY: "BIWEEKLY",
  MONTHLY: "MONTHLY",
  YEARLY: "YEARLY",
};

const TodoComponent = () => {
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [frequency, setFrequency] = useState(FrequencyEnum.MONTHLY);
  const [numberOfInstallments, setNumberOfInstallments] = useState("");
  const [bcAmount, setBcAmount] = useState("");
  const [completed, setCompleted] = useState(false);
  const navigate = useNavigate();
  const { id } = useParams();

  function saveOrUpdateTodo(e) {
    e.preventDefault();

    // Ensure frequency is not null
    const todo = {
      title,
      description,
      frequency: frequency || FrequencyEnum.MONTHLY,
      numberOfInstallments,
      bcAmount,
      completed
    };
    console.log(todo);

    if (id) {
      updateTodo(id, todo)
        .then((response) => {
          navigate("/todos");
        })
        .catch((error) => {
          console.error(error);
        });
    } else {
      saveTodo(todo)
        .then((response) => {
          console.log(response.data);
          navigate("/todos");
        })
        .catch((error) => {
          console.error(error);
        });
    }
  }

  function pageTitle() {
    if (id) {
      return <h2 className="text-center">Update BC</h2>;
    } else {
      return <h2 className="text-center">Add New BC</h2>;
    }
  }

  useEffect(() => {
    if (id) {
      getTodo(id)
        .then((response) => {
          console.log(response.data);
          setTitle(response.data.title);
          setDescription(response.data.description);
          setFrequency(response.data.frequency);
          setNumberOfInstallments(response.data.numberOfInstallments);
          setBcAmount(response.data.bcAmount);
          setCompleted(response.data.completed);
        })
        .catch((error) => {
          console.error(error);
        });
    }
  }, [id]);

  return (
    <div className="container">
      <br /> <br />
      <div className="row">
        <div className="card col-md-6 offset-md-3 offset-md-3">
          {pageTitle()}
          <div className="card-body">
            <form>
              <div className="form-group mb-2">
                <label className="form-label">BC Title:</label>
                <input
                  type="text"
                  className="form-control"
                  placeholder="Enter BC Title"
                  name="title"
                  value={title}
                  onChange={(e) => setTitle(e.target.value)}
                />
              </div>

              <div className="form-group mb-2">
                <label className="form-label">BC Description:</label>
                <input
                  type="text"
                  className="form-control"
                  placeholder="Enter BC Description"
                  name="description"
                  value={description}
                  onChange={(e) => setDescription(e.target.value)}
                />
              </div>

              <div className="form-group mb-2">
                <label className="form-label">BC Frequency:</label>
                <select
                  className="form-control"
                  value={frequency}
                  onChange={(e) => setFrequency(e.target.value)}
                >
                  <option value={FrequencyEnum.DAILY}>DAILY</option>
                  <option value={FrequencyEnum.WEEKLY}>WEEKLY</option>
                  <option value={FrequencyEnum.BIWEEKLY}>BIWEEKLY</option>
                  <option value={FrequencyEnum.MONTHLY}>MONTHLY</option>
                  <option value={FrequencyEnum.YEARLY}>YEARLY</option>
                </select>
              </div>

              <div className="form-group mb-2">
                <label className="form-label">NOI:</label>
                <input
                  type="text"
                  className="form-control"
                  placeholder="Enter Number of Installments"
                  name="NumberOfInstallments"
                  value={numberOfInstallments}
                  onChange={(e) => setNumberOfInstallments(e.target.value)}
                />
              </div>

              <div className="form-group mb-2">
                <label className="form-label">INS Amt:</label>
                <input
                  type="text"
                  className="form-control"
                  placeholder="Enter Installment Amount"
                  name="BcAmount"
                  value={bcAmount}
                  onChange={(e) => setBcAmount(e.target.value)}
                />
              </div>

              <div className="form-group mb-2">
                <label className="form-label">BC Completed:</label>
                <select
                  className="form-control"
                  value={completed}
                  onChange={(e) => setCompleted(e.target.value === "true")}
                >
                  <option value="false">No</option>
                  <option value="true">Yes</option>
                </select>
              </div>

              <button
                className="btn btn-success"
                onClick={(e) => saveOrUpdateTodo(e)}
              >
                Submit
              </button>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
};

export default TodoComponent;
