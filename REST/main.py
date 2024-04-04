from flask import Flask, request, jsonify

app = Flask(__name__)


employees = [
]


@app.route('/employees', methods=['GET'])
def get_employees():
    return jsonify(employees)


@app.route('/employees/<int:id>', methods=['GET'])
def get_employee(id):
    employee = next((emp for emp in employees if emp['id'] == id), None)
    if employee:
        return jsonify(employee)
    else:
        return jsonify({"error": "Employee not found"}), 404


@app.route('/employees', methods=['POST'])
def add_employee():
    new_employee = request.json
    if 'id' not in new_employee or 'first_name' not in new_employee or 'last_name' not in new_employee or 'position' not in new_employee:
        return jsonify({"error": "Missing required fields"}), 400
    if any(emp['id'] == new_employee['id'] for emp in employees):
        return jsonify({"error": "Employee with this id already exists"}), 409
    employees.append(new_employee)
    return jsonify({"message": "Employee added successfully"}), 202

@app.route('/employees/<int:id>', methods=['PUT'])
def update_employee(id):
    employee = next((emp for emp in employees if emp['id'] == id), None)
    if employee:
        data = request.json
        employee.update(data)
        return jsonify({"message": "Employee updated successfully"}), 202
    else:
        return jsonify({"error": "Employee not found"}), 404

if __name__ == '__main__':
    app.run(debug=True)
