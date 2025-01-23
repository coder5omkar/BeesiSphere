import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { getAllMembers, deleteMember } from "../services/MemberService";
import { Pie } from "react-chartjs-2"; // Importing chart for the pie chart
import "chart.js/auto"; // Required for Chart.js

const MemberTable = () => {
  const { todoId } = useParams(); // Get the todoId from URL
  const navigate = useNavigate();
  const [members, setMembers] = useState([]);
  const [selectedMember, setSelectedMember] = useState(null); // For selected member details
  const [showPopup, setShowPopup] = useState(false); // For popup visibility

  useEffect(() => {
    fetchMembers();
  }, []);

  const fetchMembers = async () => {
    try {
      const response = await getAllMembers();
      const filteredMembers = response.data.filter(
        (member) => member.todo === parseInt(todoId)
      );
      setMembers(filteredMembers);
    } catch (error) {
      console.error("Error fetching members:", error);
    }
  };

  const handleAddMember = () => {
    navigate(`/members/${todoId}/add`); // Navigate to Add Member page
  };

  const handleUpdateMember = (memberId) => {
    navigate(`/members/${todoId}/edit/${memberId}`); // Navigate to Edit Member page
  };

  const handleDeleteMember = async (memberId) => {
    if (window.confirm("Are you sure you want to delete this member?")) {
      try {
        await deleteMember(memberId);
        fetchMembers(); // Refresh the table
      } catch (error) {
        console.error("Error deleting member:", error);
      }
    }
  };

  const handleViewMember = (member) => {
    setSelectedMember(member); // Set the selected member details
    setShowPopup(true); // Show the popup window
  };

  const handleViewContries = (memberId) => {
    navigate(`/members/${todoId}/contries/${memberId}`); // Navigate to Contries Component
  };

  const closePopup = () => {
    setShowPopup(false); // Hide the popup window
    setSelectedMember(null); // Clear selected member details
  };

  return (
    <div className="container my-4">
      <h2 className="text-center mb-4">Members for BC ID: {todoId}</h2>
      <div className="d-flex justify-content-end mb-3">
        <button className="btn btn-primary" onClick={handleAddMember}>
          Add Member
        </button>
      </div>

      <div className="table-responsive">
        <table className="table table-bordered table-striped">
          <thead className="thead-dark">
            <tr>
              <th>ID</th>
              <th>Name</th>
              <th>Amount Received</th>
              <th>Maturity Amount</th>
              <th>Status</th>
              <th>Date Joined</th>
              <th>Maturity Date</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {members.length > 0 ? (
              members.map((member) => (
                <tr key={member.id}>
                  <td>{member.id}</td>
                  <td>{member.name}</td>
                  <td>{member.amountReceived}</td>
                  <td>{member.maturityAmount}</td>
                  <td>{member.status}</td>
                  <td>{new Date(member.dateJoined).toLocaleDateString()}</td>
                  <td>{new Date(member.maturityDate).toLocaleDateString()}</td>
                  <td>
                    <button
                      className="btn btn-info btn-sm mb-1"
                      onClick={() => handleUpdateMember(member.id)}
                    >
                      Update
                    </button>
                    <button
                      className="btn btn-danger btn-sm mb-1 ml-1"
                      onClick={() => handleDeleteMember(member.id)}
                    >
                      Delete
                    </button>
                    <button
                      className="btn btn-primary btn-sm mb-1 ml-1"
                      onClick={() => handleViewMember(member)}
                    >
                      View Member
                    </button>
                    <button
                      className="btn btn-secondary btn-sm mb-1 ml-1"
                      onClick={() => handleViewContries(member.id)}
                    >
                      View Contries
                    </button>
                  </td>
                </tr>
              ))
            ) : (
              <tr>
                <td colSpan="8" className="text-center">
                  No members found for this Todo.
                </td>
              </tr>
            )}
          </tbody>
        </table>
      </div>

      {showPopup && selectedMember && (
        <div className="popup-overlay">
          <div className="popup">
            <button className="close-button" onClick={closePopup}>
              &times;
            </button>
            <h3 className="text-center">Member Details</h3>

            <div className="popup-content">
              {/* Left Section - Member Details */}
              <div className="popup-details">
                <p>
                  <strong>ID:</strong> {selectedMember.id}
                </p>
                <p>
                  <strong>Name:</strong> {selectedMember.name}
                </p>
                <p>
                  <strong>Email:</strong> {selectedMember.email}
                </p>
                <p>
                  <strong>Phone Number:</strong> {selectedMember.phoneNumber}
                </p>
                <p>
                  <strong>Address:</strong> {selectedMember.address}
                </p>
                <p>
                  <strong>Amount Received:</strong>{" "}
                  {selectedMember.amountReceived}
                </p>
                <p>
                  <strong>Maturity Amount:</strong>{" "}
                  {selectedMember.maturityAmount}
                </p>
                <p>
                  <strong>Status:</strong> {selectedMember.status}
                </p>
                <p>
                  <strong>Date Joined:</strong>{" "}
                  {new Date(selectedMember.dateJoined).toLocaleDateString()}
                </p>
                <p>
                  <strong>Maturity Date:</strong>{" "}
                  {new Date(selectedMember.maturityDate).toLocaleDateString()}
                </p>
              </div>

              {/* Right Section - Pie Chart */}
              <div className="popup-chart">
                <h4 className="text-center">Financial Overview</h4>
                <Pie
                  data={{
                    labels: ["Amount Received", "Maturity Amount"],
                    datasets: [
                      {
                        data: [
                          selectedMember.amountReceived,
                          selectedMember.maturityAmount,
                        ],
                        backgroundColor: ["#4CAF50", "#FFC107"],
                      },
                    ],
                  }}
                  options={{ maintainAspectRatio: true }}
                />
              </div>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default MemberTable;
