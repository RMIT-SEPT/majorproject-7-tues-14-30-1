import React from "react";
import Register from "./Register";
import {shallow} from "enzyme";
import Enzyme from "enzyme";
import Adapter from "enzyme-adapter-react-16";

Enzyme.configure({adapter: new Adapter()});

describe("Register", () => {
    let wrapper;
    let mockSubmit;
    beforeEach(() => {
      mockSubmit = jest.fn();
      wrapper = shallow(<Register submit={mockSubmit} />);
    });

    //Testing the render of the component
    it("should match the snapshot", () => {
        expect(wrapper).toMatchSnapshot();
      });

    describe("handleChange", () => {
        it("should call setState on firstName", () => {
          const mockEvent = {
            target: {
              name: "firstName",
              value: "test"
            }
          };
          const expected = {
            firstName: "test",
            placeholder: "First Name",
            submitActive: false
          };
          wrapper.instance().handleChange(mockEvent);
        
          expect(wrapper.state()).toEqual(expected);
        });

        it("should call formErrors", () => {
            const spy = jest.spyOn(wrapper.instance(), "formErrors");
            
            wrapper.instance().forceUpdate();
            const mockEvent = {
              target: {
                name: "lastName",
                value: "test"
              }
            };
            const expected = true;
            wrapper.instance().handleChange(mockEvent);
            expect(spy).toHaveBeenCalled();
          });

          it("should call handleChange on lastName change with the correct params", () => {
            const spy = jest.spyOn(wrapper.instance(), "handleChange");
            wrapper.instance().forceUpdate();
            const mockEvent = {
              target: {
                name: "lastName",
                value: "test"
              }
            };
            wrapper.find(".lastName").simulate("change", mockEvent);
            expect(spy).toHaveBeenCalledWith(mockEvent);
          });

          it("should call preventDefault", () => {
            const mockPreventDefault = jest.fn();
            const mockEvent = {
              preventDefault: mockPreventDefault
            };
            wrapper.instance().handleSubmit(mockEvent);
            expect(mockPreventDefault).toHaveBeenCalled();
          });

    });
})
