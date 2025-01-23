import React, { useEffect, useState } from "react";
import {
  completeTodo,
  deleteTodo,
  getAllTodos,
  inCompleteTodo,
} from "../services/TodoService";
import { useNavigate } from "react-router-dom";

const ListTodoComponent = () => {
  const [todos, setTodos] = useState([]);
  const [selectedTodo, setSelectedTodo] = useState(null); // For popup form data
  const navigate = useNavigate();

  useEffect(() => {
    listTodos();
  }, []);

  function listTodos() {
    getAllTodos()
      .then((response) => {
        setTodos(response.data);
      })
      .catch((error) => {
        console.error(error);
      });
  }

  function addNewTodo() {
    navigate("/add-todo");
  }

  function updateTodo(id) {
    navigate(`/update-todo/${id}`);
  }

  function removeTodo(id) {
    const isConfirmed = window.confirm(
      "Business Collection will be deleted and will not be available in the main panel. Are you sure you want to delete?"
    );
  
    if (isConfirmed) {
      deleteTodo(id)
        .then(() => {
          listTodos(); // Refresh the todo list after deletion
        })
        .catch((error) => {
          console.error(error);
        });
    }
  }

  function markCompleteTodo(id) {
    completeTodo(id)
      .then(() => {
        listTodos(); // Refresh the todo list after marking complete
      })
      .catch((error) => {
        console.error(error);
      });
  }

  function markInCompleteTodo(id) {
    inCompleteTodo(id)
      .then(() => {
        listTodos(); // Refresh the todo list after marking incomplete
      })
      .catch((error) => {
        console.error(error);
      });
  }

  function viewMembers(todo) {
    navigate(`/members/${todo.id}`); // Navigate to the member details page
  }

  function viewTodoDetails(todo) {
    const personData = {
      photo: "https://via.placeholder.com/150", // Placeholder photo
      name: todo.title, // Assuming title as name
      address: "123, Main Street, City", // Example address
      mobile: "9876543210", // Example mobile number
      totalAmount: "50,000", // Example total amount
      maturityAmount: "1,00,000", // Example maturity amount
    };
    setSelectedTodo(personData); // Show popup with formatted data
  }

  function closePopup() {
    setSelectedTodo(null); // Close the popup
  }

  return (
    <div className="container">
      <h2 className="text-center">MAIN PANEL</h2>
      <button className="btn btn-primary mb-2" onClick={addNewTodo}>
        Add New BC
      </button>
      <div>
        <table className="table table-bordered table-striped">
          <thead>
            <tr>
              <th>Sr No</th> {/* Serial Number Column */}
              <th>BC Id</th>
              <th>BC Title</th>
              <th>BC Description</th>
              <th>BC Frequncy</th>
              <th>NOI</th>
              <th>INT Amt</th>
              <th>BC Completed</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {todos.map((todo, index) => (
              <tr key={todo.id}>
                <td>{index + 1}</td> {/* Dynamic serial number */}
                <td>{todo.id}</td>
                <td>{todo.title}</td>
                <td>{todo.description}</td>
                <td>{todo.frequency}</td>
                <td>{todo.numberOfInstallments}</td>
                <td>{todo.bcAmount}</td>
                <td>{todo.completed ? "YES" : "NO"}</td>
                <td>
                  <button
                    className="btn btn-info btn-sm"
                    onClick={() => updateTodo(todo.id)}
                  >
                    Update
                  </button>
                  <button
                    className="btn btn-danger btn-sm"
                    onClick={() => removeTodo(todo.id)}
                    style={{ marginLeft: "10px" }}
                  >
                    Delete
                  </button>
                  <button
                    className="btn btn-success btn-sm"
                    onClick={() => markCompleteTodo(todo.id)}
                    style={{ marginLeft: "10px" }}
                  >
                    Complete
                  </button>
                  <button
                    className="btn btn-info btn-sm"
                    onClick={() => markInCompleteTodo(todo.id)}
                    style={{ marginLeft: "10px" }}
                  >
                    In Complete
                  </button>
                  <button
                    className="btn btn-success btn-sm"
                    onClick={() => viewMembers(todo)}
                    style={{ marginLeft: "10px" }}
                  >
                    View Member Details
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
      {selectedTodo && <PopupForm person={selectedTodo} onClose={closePopup} />}
    </div>
  );
};

export default ListTodoComponent;
